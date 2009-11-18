/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors. 
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.identity.federation.core.wstrust;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;

import junit.framework.TestCase;

import org.jboss.identity.federation.core.exceptions.ConfigurationException;
import org.jboss.identity.federation.core.wstrust.JBossSTS;
import org.jboss.identity.federation.core.wstrust.STSConfiguration;
import org.jboss.identity.federation.core.wstrust.SecurityTokenProvider;
import org.jboss.identity.federation.core.wstrust.StandardRequestHandler;
import org.jboss.identity.federation.core.wstrust.WSTrustConstants;
import org.jboss.identity.federation.core.wstrust.WSTrustException;
import org.jboss.identity.federation.core.wstrust.WSTrustJAXBFactory;
import org.jboss.identity.federation.core.wstrust.WSTrustRequestHandler;
import org.jboss.identity.federation.core.wstrust.WSTrustUtil;
import org.jboss.identity.federation.core.wstrust.plugins.saml.SAML20TokenProvider;
import org.jboss.identity.federation.core.wstrust.plugins.saml.SAMLUtil;
import org.jboss.identity.federation.core.wstrust.wrappers.BaseRequestSecurityTokenResponse;
import org.jboss.identity.federation.core.wstrust.wrappers.Lifetime;
import org.jboss.identity.federation.core.wstrust.wrappers.RequestSecurityToken;
import org.jboss.identity.federation.core.wstrust.wrappers.RequestSecurityTokenResponse;
import org.jboss.identity.federation.core.wstrust.wrappers.RequestSecurityTokenResponseCollection;
import org.jboss.identity.federation.saml.v2.assertion.AssertionType;
import org.jboss.identity.federation.saml.v2.assertion.AudienceRestrictionType;
import org.jboss.identity.federation.saml.v2.assertion.ConditionAbstractType;
import org.jboss.identity.federation.saml.v2.assertion.ConditionsType;
import org.jboss.identity.federation.saml.v2.assertion.NameIDType;
import org.jboss.identity.federation.saml.v2.assertion.SubjectConfirmationDataType;
import org.jboss.identity.federation.saml.v2.assertion.SubjectConfirmationType;
import org.jboss.identity.federation.ws.addressing.AttributedURIType;
import org.jboss.identity.federation.ws.addressing.EndpointReferenceType;
import org.jboss.identity.federation.ws.addressing.ObjectFactory;
import org.jboss.identity.federation.ws.policy.AppliesTo;
import org.jboss.identity.federation.ws.trust.BinarySecretType;
import org.jboss.identity.federation.ws.trust.EntropyType;
import org.jboss.identity.federation.ws.trust.RenewTargetType;
import org.jboss.identity.federation.ws.trust.RequestedProofTokenType;
import org.jboss.identity.federation.ws.trust.RequestedReferenceType;
import org.jboss.identity.federation.ws.trust.RequestedSecurityTokenType;
import org.jboss.identity.federation.ws.trust.StatusType;
import org.jboss.identity.federation.ws.trust.ValidateTargetType;
import org.jboss.identity.federation.ws.wss.secext.KeyIdentifierType;
import org.jboss.identity.federation.ws.wss.secext.SecurityTokenReferenceType;
import org.jboss.identity.xmlsec.w3.xmldsig.KeyInfoType;
import org.jboss.identity.xmlsec.w3.xmldsig.X509DataType;
import org.jboss.identity.xmlsec.w3.xmlenc.EncryptedKeyType;
import org.w3c.dom.Element;

/**
 * <p>
 * This {@code TestCase} tests the behavior of the {@code JBossSTS} service.
 * </p>
 * 
 * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
 */
public class JBossSTSUnitTestCase extends TestCase
{

   private TestSTS tokenService;

   /*
    * (non-Javadoc)
    * 
    * @see junit.framework.TestCase#setUp()
    */
   @Override
   protected void setUp() throws Exception
   {
      super.setUp();
      // for testing purposes we can instantiate the TestSTS as a regular POJO.
      this.tokenService = new TestSTS();
      TestContext context = new TestContext();
      context.setUserPrincipal(new TestPrincipal("sguilhen"));
      this.tokenService.setContext(context);
   }

   /**
    * <p>
    * This test verifies that the STS service can read and load all configuration parameters correctly. The
    * configuration file (jboss-sts.xml) looks like the following:
    * 
    * <pre>
    *    &lt;JBossSTS xmlns=&quot;urn:jboss:identity-federation:config:1.0&quot;
    *     STSName=&quot;Test STS&quot; TokenTimeout=&quot;7200&quot; EncryptToken=&quot;true&quot;&gt;
    *     &lt;KeyProvider ClassName=&quot;org.jboss.identity.federation.bindings.tomcat.KeyStoreKeyManager&quot;&gt;
    *         &lt;Auth Key=&quot;KeyStoreURL&quot; Value=&quot;keystore/sts_keystore.jks&quot;/&gt; 
    *         &lt;Auth Key=&quot;KeyStorePass&quot; Value=&quot;testpass&quot;/&gt;
    *         &lt;Auth Key=&quot;SigningKeyAlias&quot; Value=&quot;sts&quot;/&gt;
    *         &lt;Auth Key=&quot;SigningKeyPass&quot; Value=&quot;keypass&quot;/&gt;
    *         &lt;ValidatingAlias Key=&quot;http://services.testcorp.org/provider1&quot; Value=&quot;service1&quot;/&gt;
    *         &lt;ValidatingAlias Key=&quot;http://services.testcorp.org/provider2&quot; Value=&quot;service2&quot;/&gt;
    *     &lt;/KeyProvider&gt;
    *     &lt;RequestHandler&gt;org.jboss.identity.federation.core.wstrust.StandardRequestHandler&lt;/RequestHandler&gt;
    *     &lt;TokenProviders&gt;
    *         &lt;TokenProvider ProviderClass=&quot;org.jboss.test.identity.federation.bindings.trust.SpecialTokenProvider&quot;
    *             TokenType=&quot;http://www.tokens.org/SpecialToken&quot;/&gt;
    *         &lt;TokenProvider ProviderClass=&quot;org.jboss.identity.federation.core.wstrust.SAML20TokenProvider&quot;
    *             TokenType=&quot;http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0&quot;/&gt;
    *     &lt;/TokenProviders&gt;
    *     &lt;ServiceProviders&gt;
    *         &lt;ServiceProvider Endpoint=&quot;http://services.testcorp.org/provider1&quot; TokenType=&quot;http://www.tokens.org/SpecialToken&quot;
    *             TruststoreAlias=&quot;service1&quot;/&gt;
    *         &lt;ServiceProvider Endpoint=&quot;http://services.testcorp.org/provider2&quot; TokenType=&quot;http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0&quot;
    *             TruststoreAlias=&quot;service2&quot;/&gt;
    *     &lt;/ServiceProviders&gt;
    *    &lt;/JBossSTS&gt;    *
    * </pre>
    * 
    * </p>
    * 
    * @throws Exception
    *            if an error occurs while running the test.
    */
   public void testSTSConfiguration() throws Exception
   {
      // make the STS read the configuration file.
      STSConfiguration config = this.tokenService.getConfiguration();

      // check the values that have been configured.
      assertEquals("Unexpected service name", "Test STS", config.getSTSName());
      assertEquals("Unexpected token timeout value", 7200 * 1000, config.getIssuedTokenTimeout());
      assertFalse("Encrypt token should be true", config.encryptIssuedToken());
      WSTrustRequestHandler handler = config.getRequestHandler();
      assertNotNull("Unexpected null request handler found", handler);
      assertTrue("Unexpected request handler type", handler instanceof StandardRequestHandler);

      // check the token type -> token provider mapping.
      SecurityTokenProvider provider = config.getProviderForTokenType("http://www.tokens.org/SpecialToken");
      assertNotNull("Unexpected null token provider", provider);
      assertTrue("Unexpected token provider type", provider instanceof SpecialTokenProvider);
      Map<String, String> properties = ((SpecialTokenProvider) provider).getProperties();
      assertNotNull("Unexpected null properties map", properties);
      assertEquals("Unexpected number of properties", 2, properties.size());
      assertEquals("Invalid property found", "Value1", properties.get("Property1"));
      assertEquals("Invalid property found", "Value2", properties.get("Property2"));
      provider = config.getProviderForTokenType(SAMLUtil.SAML2_TOKEN_TYPE);
      assertNotNull("Unexpected null token provider", provider);
      assertTrue("Unexpected token provider type", provider instanceof SAML20TokenProvider);
      assertNull(config.getProviderForTokenType("unexistentType"));

      // check the service provider -> token provider mapping.
      provider = config.getProviderForService("http://services.testcorp.org/provider1");
      assertNotNull("Unexpected null token provider", provider);
      assertTrue("Unexpected token provider type", provider instanceof SpecialTokenProvider);
      provider = config.getProviderForService("http://services.testcorp.org/provider2");
      assertNotNull("Unexpected null token provider", provider);
      assertTrue("Unexpected token provider type", provider instanceof SAML20TokenProvider);
      assertNull(config.getProviderForService("http://invalid.service/service"));

      // check the token element and namespace -> token provider mapping.
      provider = config.getProviderForTokenElementNS("SpecialToken", "http://www.tokens.org");
      assertNotNull("Unexpected null token provider", provider);
      assertTrue("Unexpected token provider type", provider instanceof SpecialTokenProvider);
      provider = config.getProviderForTokenElementNS("Assertion", "urn:oasis:names:tc:SAML:2.0:assertion");
      assertNotNull("Unexpected null token provider", provider);
      assertTrue("Unexpected token provider type", provider instanceof SAML20TokenProvider);
      assertNull(config.getProviderForTokenElementNS("SpecialToken", "InvalidNamespace"));

      // check the service provider -> token type mapping.
      assertEquals("Invalid token type for service provider 1", "http://www.tokens.org/SpecialToken", config
            .getTokenTypeForService("http://services.testcorp.org/provider1"));
      assertEquals("Invalid token type for service provider 2", SAMLUtil.SAML2_TOKEN_TYPE, config
            .getTokenTypeForService("http://services.testcorp.org/provider2"));
      assertNull(config.getTokenTypeForService("http://invalid.service/service"));

      // check the keystore configuration.
      assertNotNull("Invalid null STS key pair", config.getSTSKeyPair());
      assertNotNull("Invalid null STS public key", config.getSTSKeyPair().getPublic());
      assertNotNull("Invalid null STS private key", config.getSTSKeyPair().getPrivate());
      assertNotNull("Invalid null validating key for service provider 1", config
            .getServiceProviderPublicKey("http://services.testcorp.org/provider1"));
      assertNotNull("Invalid null validating key for service provider 2", config
            .getServiceProviderPublicKey("http://services.testcorp.org/provider2"));
   }

   /**
    * <p>
    * This tests sends a security token request to JBossSTS custom {@code SpecialTokenProvider}. The returned response
    * is verified to make sure the expected tokens have been returned by the service. The token that is generated in
    * this test looks as follows:
    * 
    * <pre>
    *    &lt;token:SpecialToken xmlns:token=&quot;http://www.tokens.org&quot; TokenType=&quot;http://www.tokens.org/SpecialToken&quot;&gt;
    *       Principal:sguilhen
    *    &lt;/token:SpecialToken&gt;
    * </pre>
    * 
    * </p>
    * 
    * @throws Exception
    *            if an error occurs while running the test.
    */
   public void testInvokeCustom() throws Exception
   {
      // create a simple token request, asking for a "special" test token.
      RequestSecurityToken request = this.createRequest("testcontext", WSTrustConstants.ISSUE_REQUEST,
            "http://www.tokens.org/SpecialToken", null);

      // use the factory to marshall the request.
      WSTrustJAXBFactory factory = WSTrustJAXBFactory.getInstance();
      Source requestMessage = factory.marshallRequestSecurityToken(request);

      // invoke the token service.
      Source responseMessage = this.tokenService.invoke(requestMessage);
      BaseRequestSecurityTokenResponse baseResponse = WSTrustJAXBFactory.getInstance()
            .parseRequestSecurityTokenResponse(responseMessage);

      // validate the security token response.
      this.validateCustomTokenResponse(baseResponse);
   }

   /**
    * <p>
    * This tests sends a SAMLV2.0 security token request to JBossSTS. This request should be handled by the standard
    * {@code SAML20TokenProvider} and should result in a SAMLV2.0 assertion that looks like the following:
    * 
    * <pre>
    * &lt;saml2:Assertion xmlns:saml2=&quot;urn:oasis:names:tc:SAML:2.0:assertion&quot; 
    *                  xmlns:ds=&quot;http://www.w3.org/2000/09/xmldsig#&quot; 
    *                  xmlns:xenc=&quot;http://www.w3.org/2001/04/xmlenc#&quot; 
    *                  ID=&quot;ID-cc541137-74dc-4fc0-8bcc-7e9e3a4c899d&quot;
    *                  IssueInstant=&quot;2009-05-29T18:02:13.458Z&quot;&gt;
    *     &lt;saml2:Issuer&gt;
    *         JBossSTS
    *     &lt;/saml2:Issuer&gt;
    *     &lt;saml2:Subject&gt;
    *         &lt;saml2:NameID NameQualifier=&quot;http://www.jboss.org&quot;&gt;
    *             sguilhen
    *         &lt;/saml2:NameID&gt;
    *         &lt;saml2:SubjectConfirmation Method=&quot;urn:oasis:names:tc:SAML:2.0:cm:bearer&quot;/&gt;
    *     &lt;/saml2:Subject&gt;
    *     &lt;saml2:Conditions NotBefore=&quot;2009-05-29T18:02:13.458Z&quot; NotOnOrAfter=&quot;2009-05-29T19:02:13.458Z&quot;&gt;
    *         &lt;saml2:AudienceRestriction&gt;
    *             &lt;saml2:Audience&gt;
    *                 http://services.testcorp.org/provider2
    *             &lt;/saml2:Audience&gt;
    *         &lt;/saml2:AudienceRestriction&gt;
    *     &lt;/saml2:Conditions&gt;
    *     &lt;ds:Signature&gt;
    *         ...
    *     &lt;/ds:Signature&gt;
    * &lt;/saml2:Assertion&gt;
    * </pre>
    * 
    * </p>
    * 
    * @throws Exception
    *            if an error occurs while running the test.
    */
   public void testInvokeSAML20() throws Exception
   {
      // create a simple token request, asking for a SAMLv2.0 token.
      RequestSecurityToken request = this.createRequest("testcontext", WSTrustConstants.ISSUE_REQUEST,
            SAMLUtil.SAML2_TOKEN_TYPE, null);

      // use the factory to marshall the request.
      WSTrustJAXBFactory factory = WSTrustJAXBFactory.getInstance();
      Source requestMessage = factory.marshallRequestSecurityToken(request);

      // invoke the token service.
      Source responseMessage = this.tokenService.invoke(requestMessage);
      BaseRequestSecurityTokenResponse baseResponse = WSTrustJAXBFactory.getInstance()
            .parseRequestSecurityTokenResponse(responseMessage);

      // validate the security token response.
      this.validateSAMLAssertionResponse(baseResponse, "testcontext", SAMLUtil.SAML2_BEARER_URI);
   }

   /**
    * <p>
    * This test requests a token to the STS using the {@code AppliesTo} to identify the service provider. The STS must
    * be able to find out the type of the token that must be issued using the service provider URI. In this specific
    * case, the request should be handled by the custom {@code SpecialTokenProvider}.
    * </p>
    * 
    * @throws Exception
    *            if an error occurs while running the test.
    */
   public void testInvokeCustomAppliesTo() throws Exception
   {
      // create a simple token request, this time using the applies to get to the token type.
      RequestSecurityToken request = this.createRequest("testcontext", WSTrustConstants.ISSUE_REQUEST, null,
            "http://services.testcorp.org/provider1");

      // use the factory to marshall the request.
      WSTrustJAXBFactory factory = WSTrustJAXBFactory.getInstance();
      Source requestMessage = factory.marshallRequestSecurityToken(request);

      // invoke the token service.
      Source responseMessage = this.tokenService.invoke(requestMessage);
      BaseRequestSecurityTokenResponse baseResponse = WSTrustJAXBFactory.getInstance()
            .parseRequestSecurityTokenResponse(responseMessage);

      // validate the security token response.
      this.validateCustomTokenResponse(baseResponse);
   }

   /**
    * <p>
    * This test requests a token to the STS using the {@code AppliesTo} to identify the service provider. The STS must
    * be able to find out the type of the token that must be issued using the service provider URI. In this specific
    * case, the request should be handled by the standard {@code SAML20TokenProvider}.
    * </p>
    * 
    * @throws Exception
    *            if an error occurs while running the test.
    */
   public void testInvokeSAML20AppliesTo() throws Exception
   {
      RequestSecurityToken request = this.createRequest("testcontext", WSTrustConstants.ISSUE_REQUEST, null,
            "http://services.testcorp.org/provider2");

      // use the factory to marshall the request.
      WSTrustJAXBFactory factory = WSTrustJAXBFactory.getInstance();
      Source requestMessage = factory.marshallRequestSecurityToken(request);

      // invoke the token service.
      Source responseMessage = this.tokenService.invoke(requestMessage);
      BaseRequestSecurityTokenResponse baseResponse = WSTrustJAXBFactory.getInstance()
            .parseRequestSecurityTokenResponse(responseMessage);

      // validate the security token response.
      AssertionType assertion = this.validateSAMLAssertionResponse(baseResponse, "testcontext",
            SAMLUtil.SAML2_BEARER_URI);

      // in this scenario, the conditions section should have an audience restriction.
      ConditionsType conditions = assertion.getConditions();
      assertEquals("Unexpected restriction list size", 1, conditions.getConditionOrAudienceRestrictionOrOneTimeUse()
            .size());
      ConditionAbstractType abstractType = conditions.getConditionOrAudienceRestrictionOrOneTimeUse().get(0);
      assertTrue("Unexpected restriction type", abstractType instanceof AudienceRestrictionType);
      AudienceRestrictionType audienceRestriction = (AudienceRestrictionType) abstractType;
      assertEquals("Unexpected audience restriction list size", 1, audienceRestriction.getAudience().size());
      assertEquals("Unexpected audience restriction item", "http://services.testcorp.org/provider2",
            audienceRestriction.getAudience().get(0));
   }

   /**
    * <p>
    * This test requests a SAMLV2.0 assertion and requires a symmetric key to be used as a proof-of-possession token.
    * As the request doesn't contain any client-specified key, the STS is responsible for generating a random key and
    * use this key as the proof token. The WS-Trust response should contain the STS-generated key.
    * </p>
    * 
    * @throws Exception if an error occurs while running the test.
    */
   @SuppressWarnings("unchecked")
   public void testInvokeSAML20WithSTSGeneratedSymmetricKey() throws Exception
   {
      // create a simple token request, asking for a SAMLv2.0 token.
      RequestSecurityToken request = this.createRequest("testcontext", WSTrustConstants.ISSUE_REQUEST, null,
            "http://services.testcorp.org/provider2");

      // add a symmetric key type to the request, but don't supply any client key - STS should generate one.
      request.setKeyType(URI.create(WSTrustConstants.KEY_TYPE_SYMMETRIC));

      // use the factory to marshall the request.
      WSTrustJAXBFactory factory = WSTrustJAXBFactory.getInstance();
      Source requestMessage = factory.marshallRequestSecurityToken(request);

      // invoke the token service.
      Source responseMessage = this.tokenService.invoke(requestMessage);
      BaseRequestSecurityTokenResponse baseResponse = WSTrustJAXBFactory.getInstance()
            .parseRequestSecurityTokenResponse(responseMessage);

      // validate the security token response.
      this.validateSAMLAssertionResponse(baseResponse, "testcontext", SAMLUtil.SAML2_HOLDER_OF_KEY_URI);

      // check if the response contains the STS-generated key.
      RequestSecurityTokenResponseCollection collection = (RequestSecurityTokenResponseCollection) baseResponse;
      RequestSecurityTokenResponse response = collection.getRequestSecurityTokenResponses().get(0);
      RequestedProofTokenType proofToken = response.getRequestedProofToken();
      assertNotNull("Unexpected null proof token", proofToken);
      assertTrue(proofToken.getAny() instanceof JAXBElement);
      JAXBElement proofElement = (JAXBElement) proofToken.getAny();
      assertEquals("Unexpected proof token content", BinarySecretType.class, proofElement.getDeclaredType());
      BinarySecretType serverBinarySecret = (BinarySecretType) proofElement.getValue();
      assertNotNull("Unexpected null secret", serverBinarySecret.getValue());
      // default key size is 128 bits (16 bytes).
      assertEquals("Unexpected secret size", 16, serverBinarySecret.getValue().length);
   }

   /**
    * <p>
    * This test requests a SAMLV2.0 assertion and requires a symmetric key to be used as a proof-of-possession token.
    * In this case, the client supplies a secret key in the WS-Trust request, so the STS should combine the client-
    * specified key with the STS-generated key and use this combined key as the proof token. The WS-Trust response
    * should include the STS key to allow reconstruction of the combined key and the algorithm used to combine the keys.
    * </p>
    * 
    * @throws Exception if an error occurs while running the test.
    */
   @SuppressWarnings("unchecked")
   public void testInvokeSAML20WithCombinedSymmetricKey() throws Exception
   {
      // create a 128-bit random client secret.
      byte[] clientSecret = WSTrustUtil.createRandomSecret(16);
      BinarySecretType clientBinarySecret = new BinarySecretType();
      clientBinarySecret.setType(WSTrustConstants.BS_TYPE_NONCE);
      clientBinarySecret.setValue(clientSecret);

      // set the client secret in the client entropy.
      EntropyType clientEntropy = new EntropyType();
      clientEntropy.getAny().add(
            new org.jboss.identity.federation.ws.trust.ObjectFactory().createBinarySecret(clientBinarySecret));

      // create a token request specifying the key type, key size, and client entropy. 
      RequestSecurityToken request = this.createRequest("testcontext", WSTrustConstants.ISSUE_REQUEST, null,
            "http://services.testcorp.org/provider2");
      request.setKeyType(URI.create(WSTrustConstants.KEY_TYPE_SYMMETRIC));
      request.setEntropy(clientEntropy);
      request.setKeySize(64);

      // invoke the token service.
      Source requestMessage = WSTrustJAXBFactory.getInstance().marshallRequestSecurityToken(request);
      Source responseMessage = this.tokenService.invoke(requestMessage);
      BaseRequestSecurityTokenResponse baseResponse = WSTrustJAXBFactory.getInstance()
            .parseRequestSecurityTokenResponse(responseMessage);

      // validate the security token response.
      this.validateSAMLAssertionResponse(baseResponse, "testcontext", SAMLUtil.SAML2_HOLDER_OF_KEY_URI);

      RequestSecurityTokenResponseCollection collection = (RequestSecurityTokenResponseCollection) baseResponse;
      RequestSecurityTokenResponse response = collection.getRequestSecurityTokenResponses().get(0);
      RequestedProofTokenType proofToken = response.getRequestedProofToken();
      assertNotNull("Unexpected null proof token", proofToken);
      assertTrue(proofToken.getAny() instanceof JAXBElement);
      JAXBElement<?> proofElement = (JAXBElement<?>) proofToken.getAny();

      // proof token should contain only the computed key algorithm.
      assertEquals("Unexpected proof token content", "ComputedKey", proofElement.getName().getLocalPart());
      assertEquals("Unexpected computed key algorithm", WSTrustConstants.CK_PSHA1, proofElement.getValue());

      // server entropy must have been included in the response to allow reconstruction of the computed key.
      EntropyType serverEntropy = response.getEntropy();
      assertNotNull("Unexpected null server entropy");
      assertEquals("Invalid number of elements in server entropy", 1, serverEntropy.getAny().size());
      JAXBElement serverEntropyContent = (JAXBElement) serverEntropy.getAny().get(0);
      assertEquals("Unexpected proof token content", BinarySecretType.class, serverEntropyContent.getDeclaredType());
      BinarySecretType serverBinarySecret = (BinarySecretType) serverEntropyContent.getValue();
      assertEquals("Unexpected binary secret type", WSTrustConstants.BS_TYPE_NONCE, serverBinarySecret.getType());
      assertNotNull("Unexpected null secret value", serverBinarySecret.getValue());
      assertEquals("Unexpected secret size", 8, serverBinarySecret.getValue().length);
   }

   /**
    * <p>
    * This test case first generates a SAMLV2.0 assertion and then sends a WS-Trust validate message to the STS to get
    * the assertion validated, checking the validation results.
    * </p>
    * 
    * @throws Exception
    *            if an error occurs while running the test.
    */
   public void testInvokeSAML20Validate() throws Exception
   {
      // create a simple token request.
      RequestSecurityToken request = this.createRequest("testcontext", WSTrustConstants.ISSUE_REQUEST,
            SAMLUtil.SAML2_TOKEN_TYPE, null);

      // use the factory to marshall the request.
      WSTrustJAXBFactory factory = WSTrustJAXBFactory.getInstance();
      Source requestMessage = factory.marshallRequestSecurityToken(request);

      // invoke the token service.
      Source responseMessage = this.tokenService.invoke(requestMessage);
      BaseRequestSecurityTokenResponse baseResponse = factory.parseRequestSecurityTokenResponse(responseMessage);

      // validate the response and get the SAML assertion from the request.
      this.validateSAMLAssertionResponse(baseResponse, "testcontext", SAMLUtil.SAML2_BEARER_URI);
      RequestSecurityTokenResponseCollection collection = (RequestSecurityTokenResponseCollection) baseResponse;
      Element assertion = (Element) collection.getRequestSecurityTokenResponses().get(0).getRequestedSecurityToken()
            .getAny();

      // now construct a WS-Trust validate request with the generated assertion.
      request = this.createRequest("validatecontext", WSTrustConstants.VALIDATE_REQUEST, WSTrustConstants.STATUS_TYPE,
            null);
      ValidateTargetType validateTarget = new ValidateTargetType();
      validateTarget.setAny(assertion);
      request.setValidateTarget(validateTarget);

      // invoke the token service.
      responseMessage = this.tokenService.invoke(factory.marshallRequestSecurityToken(request));
      baseResponse = factory.parseRequestSecurityTokenResponse(responseMessage);

      // validate the response contents.
      assertNotNull("Unexpected null response", baseResponse);
      assertTrue("Unexpected response type", baseResponse instanceof RequestSecurityTokenResponseCollection);
      collection = (RequestSecurityTokenResponseCollection) baseResponse;
      assertEquals("Unexpected number of responses", 1, collection.getRequestSecurityTokenResponses().size());
      RequestSecurityTokenResponse response = collection.getRequestSecurityTokenResponses().get(0);
      assertEquals("Unexpected response context", "validatecontext", response.getContext());
      assertEquals("Unexpected token type", WSTrustConstants.STATUS_TYPE, response.getTokenType().toString());
      StatusType status = response.getStatus();
      assertNotNull("Unexpected null status", status);
      assertEquals("Unexpected status code", WSTrustConstants.STATUS_CODE_VALID, status.getCode());
      assertEquals("Unexpected status reason", "SAMLV2.0 Assertion successfuly validated", status.getReason());

      // now let's temper the SAML assertion and try to validate it again.
      assertion.getFirstChild().getFirstChild().setNodeValue("Tempered Issuer");
      request.getValidateTarget().setAny(assertion);
      responseMessage = this.tokenService.invoke(factory.marshallRequestSecurityToken(request));
      collection = (RequestSecurityTokenResponseCollection) WSTrustJAXBFactory.getInstance()
            .parseRequestSecurityTokenResponse(responseMessage);
      assertEquals("Unexpected number of responses", 1, collection.getRequestSecurityTokenResponses().size());
      response = collection.getRequestSecurityTokenResponses().get(0);
      assertEquals("Unexpected response context", "validatecontext", response.getContext());
      assertEquals("Unexpected token type", WSTrustConstants.STATUS_TYPE, response.getTokenType().toString());
      status = response.getStatus();
      assertNotNull("Unexpected null status", status);
      assertEquals("Unexpected status code", WSTrustConstants.STATUS_CODE_INVALID, status.getCode());
      assertEquals("Unexpected status reason", "Validation failure: digital signature is invalid", status.getReason());
   }

   /**
    * <p>
    * This test case first generates a SAMLV2.0 assertion and then sends a WS-Trust renew message to the STS to get
    * the assertion renewed (i.e. get a new assertion with an updated lifetime).
    * </p>
    * 
    * @throws Exception
    *            if an error occurs while running the test.
    */
   public void testInvokeSAML20Renew() throws Exception
   {
      // create a simple token request, using applies-to to identify the token type.
      RequestSecurityToken request = this.createRequest("testcontext", WSTrustConstants.ISSUE_REQUEST, null,
            "http://services.testcorp.org/provider2");

      // use the factory to marshall the request.
      WSTrustJAXBFactory factory = WSTrustJAXBFactory.getInstance();
      Source requestMessage = factory.marshallRequestSecurityToken(request);

      // invoke the token service.
      Source responseMessage = this.tokenService.invoke(requestMessage);
      BaseRequestSecurityTokenResponse baseResponse = factory.parseRequestSecurityTokenResponse(responseMessage);

      // validate the response and get the SAML assertion from the request.
      this.validateSAMLAssertionResponse(baseResponse, "testcontext", SAMLUtil.SAML2_BEARER_URI);
      RequestSecurityTokenResponseCollection collection = (RequestSecurityTokenResponseCollection) baseResponse;
      Element assertionElement = (Element) collection.getRequestSecurityTokenResponses().get(0)
            .getRequestedSecurityToken().getAny();

      // now construct a WS-Trust renew request with the generated assertion.
      request = this.createRequest("renewcontext", WSTrustConstants.RENEW_REQUEST, SAMLUtil.SAML2_TOKEN_TYPE, null);
      RenewTargetType renewTarget = new RenewTargetType();
      renewTarget.setAny(assertionElement);
      request.setRenewTarget(renewTarget);

      // invoke the token service.
      responseMessage = this.tokenService.invoke(factory.marshallRequestSecurityToken(request));
      baseResponse = factory.parseRequestSecurityTokenResponse(responseMessage);

      // validate the renew response contents and get the renewed token.
      this.validateSAMLAssertionResponse(baseResponse, "renewcontext", SAMLUtil.SAML2_BEARER_URI);
      collection = (RequestSecurityTokenResponseCollection) baseResponse;
      Element renewedAssertionElement = (Element) collection.getRequestSecurityTokenResponses().get(0)
            .getRequestedSecurityToken().getAny();

      // compare the assertions, checking if the lifetime has been updated.
      AssertionType originalAssertion = SAMLUtil.fromElement(assertionElement);
      AssertionType renewedAssertion = SAMLUtil.fromElement(renewedAssertionElement);

      // assertions should have different ids and lifetimes.
      assertFalse("Renewed assertion should have a unique id", originalAssertion.getID().equals(
            renewedAssertion.getID()));
      assertEquals(DatatypeConstants.LESSER, originalAssertion.getConditions().getNotBefore().compare(
            renewedAssertion.getConditions().getNotBefore()));
      assertEquals(DatatypeConstants.LESSER, originalAssertion.getConditions().getNotOnOrAfter().compare(
            renewedAssertion.getConditions().getNotOnOrAfter()));
   }

   /**
    * <p>
    * This test tries to request a token of an unknown type, checking if an exception is correctly thrown by the
    * security token service.
    * </p>
    * 
    * @throws Exception
    *            if an error occurs while running the test.
    */
   public void testInvokeUnknownTokenType() throws Exception
   {
      // create a simple token request, asking for an "unknown" test token.
      RequestSecurityToken request = this.createRequest("testcontext", WSTrustConstants.ISSUE_REQUEST,
            "http://www.tokens.org/UnknownToken", null);

      // use the factory to marshall the request.
      WSTrustJAXBFactory factory = WSTrustJAXBFactory.getInstance();
      Source requestMessage = factory.marshallRequestSecurityToken(request);

      // invoke the security token service.
      try
      {
         this.tokenService.invoke(requestMessage);
         fail("An exception should have been raised by the security token service");
      }
      catch (WebServiceException we)
      {
         assertEquals("Unexpected exception message", "Exception in handling token request:", we.getMessage());
         assertNotNull("Unexpected null cause", we.getCause());
         assertTrue("Unexpected cause type", we.getCause() instanceof WSTrustException);
         assertEquals("Unexpected exception message", "Unable to find a token provider for the token request", we
               .getCause().getMessage());
      }
   }

   /**
    * <p>
    * Validates the contents of a WS-Trust response message that contains a custom token issued by the test {@code
    * SpecialTokenProvider}.
    * </p>
    * 
    * @param baseResponse
    *           a reference to the WS-Trust response that was sent by the STS.
    * @throws Exception
    *            if one of the validation performed fail.
    */
   private void validateCustomTokenResponse(BaseRequestSecurityTokenResponse baseResponse) throws Exception
   {

      // =============================== WS-Trust Security Token Response Validation ===============================//

      assertNotNull("Unexpected null response", baseResponse);
      assertTrue("Unexpected response type", baseResponse instanceof RequestSecurityTokenResponseCollection);
      RequestSecurityTokenResponseCollection collection = (RequestSecurityTokenResponseCollection) baseResponse;
      assertEquals("Unexpected number of responses", 1, collection.getRequestSecurityTokenResponses().size());
      RequestSecurityTokenResponse response = collection.getRequestSecurityTokenResponses().get(0);
      assertEquals("Unexpected response context", "testcontext", response.getContext());
      assertEquals("Unexpected token type", "http://www.tokens.org/SpecialToken", response.getTokenType().toString());
      Lifetime lifetime = response.getLifetime();
      assertNotNull("Unexpected null token lifetime", lifetime);

      // ========================================= Custom Token Validation =========================================//

      RequestedSecurityTokenType requestedToken = response.getRequestedSecurityToken();
      assertNotNull("Unexpected null requested security token", requestedToken);
      Object token = requestedToken.getAny();
      assertNotNull("Unexpected null token", token);
      assertTrue("Unexpected token class", token instanceof Element);
      Element element = (Element) requestedToken.getAny();
      assertEquals("Unexpected namespace value", "http://www.tokens.org", element.getNamespaceURI());

      assertEquals("Unexpected attribute value", "http://www.tokens.org/SpecialToken", element.getAttributeNS(
            "http://www.tokens.org", "TokenType"));
      assertEquals("Unexpected token value", "Principal:sguilhen", element.getFirstChild().getNodeValue());
   }

   /**
    * <p>
    * Validates the contents of a WS-Trust response message that contains a SAMLV2.0 assertion issued by the {@code
    * SAML20TokenProvider}.
    * </p>
    * 
    * @param baseResponse
    *           a reference to the WS-Trust response that was sent by the STS.
    * @return the SAMLV2.0 assertion that has been extracted from the response. This object can be used by the test
    *         methods to perform extra validations depending on the scenario being tested.
    * @throws Exception
    *            if one of the validation performed fail.
    */
   private AssertionType validateSAMLAssertionResponse(BaseRequestSecurityTokenResponse baseResponse, String context,
         String confirmationMethod) throws Exception
   {

      // =============================== WS-Trust Security Token Response Validation ===============================//

      assertNotNull("Unexpected null response", baseResponse);
      assertTrue("Unexpected response type", baseResponse instanceof RequestSecurityTokenResponseCollection);
      RequestSecurityTokenResponseCollection collection = (RequestSecurityTokenResponseCollection) baseResponse;
      assertEquals("Unexpected number of responses", 1, collection.getRequestSecurityTokenResponses().size());
      RequestSecurityTokenResponse response = collection.getRequestSecurityTokenResponses().get(0);
      assertEquals("Unexpected response context", context, response.getContext());
      assertEquals("Unexpected token type", SAMLUtil.SAML2_TOKEN_TYPE, response.getTokenType().toString());
      Lifetime lifetime = response.getLifetime();
      assertNotNull("Unexpected null token lifetime", lifetime);

      // validate the attached token reference.
      RequestedReferenceType reference = response.getRequestedAttachedReference();
      assertNotNull("Unexpected null attached reference", reference);
      SecurityTokenReferenceType securityRef = reference.getSecurityTokenReference();
      assertNotNull("Unexpected null security reference", securityRef);
      String tokenTypeAttr = securityRef.getOtherAttributes().get(new QName(WSTrustConstants.WSSE11_NS, "TokenType"));
      assertNotNull("Required attribute TokenType is missing", tokenTypeAttr);
      assertEquals("TokenType attribute has an unexpected value", SAMLUtil.SAML2_TOKEN_TYPE, tokenTypeAttr);
      JAXBElement<?> keyIdElement = (JAXBElement<?>) securityRef.getAny().get(0);
      KeyIdentifierType keyId = (KeyIdentifierType) keyIdElement.getValue();
      assertEquals("Unexpected key value type", SAMLUtil.SAML2_VALUE_TYPE, keyId.getValueType());
      assertNotNull("Unexpected null key identifier value", keyId.getValue());

      // ====================================== SAMLV2.0 Assertion Validation ======================================//

      RequestedSecurityTokenType requestedToken = response.getRequestedSecurityToken();
      assertNotNull("Unexpected null requested security token", requestedToken);

      // unmarshall the SAMLV2.0 assertion.
      AssertionType assertion = SAMLUtil.fromElement((Element) requestedToken.getAny());

      // verify the contents of the unmarshalled assertion.
      assertNotNull("Invalid null assertion ID", assertion.getID());
      assertEquals(keyId.getValue().substring(1), assertion.getID());
      assertEquals(lifetime.getCreated(), assertion.getIssueInstant());

      // validate the assertion issuer.
      assertNotNull("Unexpected null assertion issuer", assertion.getIssuer());
      assertEquals("Unexpected assertion issuer name", "Test STS", assertion.getIssuer().getValue());

      // validate the assertion subject.
      assertNotNull("Unexpected null subject", assertion.getSubject());
      List<JAXBElement<?>> content = assertion.getSubject().getContent();
      assertNotNull("Unexpected null subject content");
      assertEquals(2, content.size());
      assertEquals("Unexpected type found", NameIDType.class, content.get(0).getDeclaredType());
      NameIDType nameID = (NameIDType) content.get(0).getValue();
      assertEquals("Unexpected name id qualifier", "urn:jboss:identity-federation", nameID.getNameQualifier());
      assertEquals("Unexpected name id value", "sguilhen", nameID.getValue());
      assertEquals("Unexpected type found", SubjectConfirmationType.class, content.get(1).getDeclaredType());
      SubjectConfirmationType subjType = (SubjectConfirmationType) content.get(1).getValue();
      assertEquals("Unexpected confirmation method", confirmationMethod, subjType.getMethod());

      // if confirmation method is holder of key, make sure the assertion contains a KeyInfo with the proof token.
      if (SAMLUtil.SAML2_HOLDER_OF_KEY_URI.equals(confirmationMethod))
      {
         SubjectConfirmationDataType subjConfirmationDataType = subjType.getSubjectConfirmationData();
         assertNotNull("Unexpected null subject confirmation data", subjConfirmationDataType);
         List<Object> confirmationContent = subjConfirmationDataType.getContent();
         assertEquals("Unexpected subject confirmation content size", 1, confirmationContent.size());
         JAXBElement<?> keyInfoElement = (JAXBElement<?>) confirmationContent.get(0);
         assertEquals("Unexpected subject confirmation context type", KeyInfoType.class, keyInfoElement
               .getDeclaredType());
         KeyInfoType keyInfo = (KeyInfoType) keyInfoElement.getValue();
         assertEquals("Unexpected key info content size", 1, keyInfo.getContent().size());

         // if they key is a symmetric key, the KeyInfo should contain an encrypted element.
         if (WSTrustConstants.KEY_TYPE_SYMMETRIC.equals(response.getKeyType().toString()))
         {
            JAXBElement<?> encKeyElement = (JAXBElement<?>) keyInfo.getContent().get(0);
            assertEquals("Unexpected key info content type", EncryptedKeyType.class, encKeyElement.getDeclaredType());
         }
         // if the key is a public key, the KeyInfo should contain an encoded certificate.
         else if (WSTrustConstants.KEY_TYPE_PUBLIC.equals(response.getKeyType().toString()))
         {
            JAXBElement<?> x509DataElement = (JAXBElement<?>) keyInfo.getContent().get(0);
            assertEquals("Unexpected key info content type", X509DataType.class, x509DataElement.getDeclaredType());
            X509DataType x509Data = (X509DataType) x509DataElement.getValue();
            assertEquals("Unexpected X509 data content size", 1, x509Data
                  .getX509IssuerSerialOrX509SKIOrX509SubjectName().size());
            JAXBElement<?> x509CertElement = (JAXBElement<?>) x509Data.getX509IssuerSerialOrX509SKIOrX509SubjectName()
                  .get(0);
            assertEquals("Unexpected X509 data content type", byte[].class, x509CertElement.getDeclaredType());
         }
      }

      // validate the assertion conditions.
      assertNotNull("Unexpected null conditions", assertion.getConditions());
      assertEquals(lifetime.getCreated(), assertion.getConditions().getNotBefore());
      assertEquals(lifetime.getExpires(), assertion.getConditions().getNotOnOrAfter());

      // verify if the assertion has been signed.
      assertNotNull("Assertion should have been signed", assertion.getSignature());

      return assertion;
   }

   /**
    * <p>
    * Utility method that creates a simple WS-Trust request using the specified information.
    * </p>
    * 
    * @param context
    *           a {@code String} that represents the request context.
    * @param requestType
    *           a {@code String} that represents the WS-Trust request type.
    * @param tokenType
    *           a {@code String} that represents the requested token type.
    * @param appliesToString
    *           a {@code String} that represents the URL of a service provider.
    * @return the constructed {@code RequestSecurityToken} object.
    */
   private RequestSecurityToken createRequest(String context, String requestType, String tokenType,
         String appliesToString)
   {
      RequestSecurityToken request = new RequestSecurityToken();
      request.setContext(context);
      request.setRequestType(URI.create(requestType));
      if (tokenType != null)
         request.setTokenType(URI.create(tokenType));
      if (appliesToString != null)
      {
         AttributedURIType attributedURI = new AttributedURIType();
         attributedURI.setValue(appliesToString);
         EndpointReferenceType reference = new EndpointReferenceType();
         reference.setAddress(attributedURI);
         AppliesTo appliesTo = new AppliesTo();
         appliesTo.getAny().add(new ObjectFactory().createEndpointReference(reference));
         request.setAppliesTo(appliesTo);
      }
      return request;
   }

   /**
    * <p>
    * Helper class that exposes the JBossSTS methods as public for the tests to work.
    * </p>
    * 
    * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
    */
   class TestSTS extends JBossSTS
   {

      @Override
      public STSConfiguration getConfiguration() throws ConfigurationException
      {
         return super.getConfiguration();
      }

      public void setContext(WebServiceContext context)
      {
         super.context = context;
      }
   }

   /**
    * <p>
    * Helper class that mocks a {@code WebServiceContext}. It is used in the JBoss STS test cases.
    * </p>
    * 
    * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
    */
   class TestContext implements WebServiceContext
   {

      private Principal principal;

      /*
       * (non-Javadoc)
       * 
       * @see javax.xml.ws.WebServiceContext#getEndpointReference(java.lang.Class, org.w3c.dom.Element[])
       */
      public <T extends EndpointReference> T getEndpointReference(Class<T> arg0, Element... arg1)
      {
         return null;
      }

      /*
       * (non-Javadoc)
       * 
       * @see javax.xml.ws.WebServiceContext#getEndpointReference(org.w3c.dom.Element[])
       */
      public EndpointReference getEndpointReference(Element... arg0)
      {
         return null;
      }

      /*
       * (non-Javadoc)
       * 
       * @see javax.xml.ws.WebServiceContext#getMessageContext()
       */
      public MessageContext getMessageContext()
      {
         return null;
      }

      /*
       * (non-Javadoc)
       * 
       * @see javax.xml.ws.WebServiceContext#getUserPrincipal()
       */
      public Principal getUserPrincipal()
      {
         return this.principal;
      }

      /**
       * <p>
       * Sets the principal to be used in the test case.
       * </p>
       * 
       * @param principal
       *           the {@code Principal} to be set.
       */
      public void setUserPrincipal(Principal principal)
      {
         this.principal = principal;
      }

      /*
       * (non-Javadoc)
       * 
       * @see javax.xml.ws.WebServiceContext#isUserInRole(java.lang.String)
       */
      public boolean isUserInRole(String arg0)
      {
         return false;
      }
   }
}
