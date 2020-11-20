/* SPDX-License-Identifier: Apache 2.0 */
/* Copyright Contributors to the ODPi Egeria project. */

package org.odpi.openmetadata.accessservices.assetmanager.client;

import org.odpi.openmetadata.accessservices.assetmanager.api.AssetManagerInterface;
import org.odpi.openmetadata.accessservices.assetmanager.client.rest.AssetManagerRESTClient;
import org.odpi.openmetadata.accessservices.assetmanager.metadataelements.ElementHeader;
import org.odpi.openmetadata.accessservices.assetmanager.properties.AssetManagerProperties;
import org.odpi.openmetadata.accessservices.assetmanager.properties.KeyPattern;
import org.odpi.openmetadata.accessservices.assetmanager.properties.MetadataCorrelationProperties;
import org.odpi.openmetadata.accessservices.assetmanager.properties.SynchronizationDirection;
import org.odpi.openmetadata.accessservices.assetmanager.rest.ElementHeadersResponse;
import org.odpi.openmetadata.commonservices.ffdc.InvalidParameterHandler;
import org.odpi.openmetadata.commonservices.ffdc.rest.GUIDResponse;
import org.odpi.openmetadata.frameworks.auditlog.AuditLog;
import org.odpi.openmetadata.frameworks.connectors.ffdc.InvalidParameterException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.PropertyServerException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.UserNotAuthorizedException;

import java.util.List;
import java.util.Map;


/**
 * AssetManagerClient is the client for setting up the SoftwareServerCapabilities that represent asset managers.
 */
public class AssetManagerClient implements AssetManagerInterface
{
    private final String assetManagerGUIDParameterName = "assetManagerGUID";
    private final String assetManagerNameParameterName = "assetManagerName";

    private String   serverName;               /* Initialized in constructor */
    private String   serverPlatformURLRoot;    /* Initialized in constructor */

    private InvalidParameterHandler invalidParameterHandler = new InvalidParameterHandler();
    private AssetManagerRESTClient  restClient;               /* Initialized in constructor */

    private AuditLog auditLog = null;

    private final String urlTemplatePrefix = "/servers/{0}/open-metadata/access-services/asset-manager/users/{1}/asset-managers";


    /**
     * Create a new client with no authentication embedded in the HTTP request.
     *
     * @param serverName name of the server to connect to
     * @param serverPlatformURLRoot the network address of the server running the OMAS REST servers
     * @param auditLog logging destination
     * @throws InvalidParameterException there is a problem creating the client-side components to issue any
     * REST API calls.
     */
    public AssetManagerClient(String   serverName,
                              String   serverPlatformURLRoot,
                              AuditLog auditLog) throws InvalidParameterException
    {
        final String methodName = "Client Constructor";

        invalidParameterHandler.validateOMAGServerPlatformURL(serverPlatformURLRoot, serverName, methodName);

        this.serverName = serverName;
        this.serverPlatformURLRoot = serverPlatformURLRoot;

        this.restClient = new AssetManagerRESTClient(serverName, serverPlatformURLRoot, auditLog);

        this.auditLog = auditLog;
    }


    /**
     * Create a new client with no authentication embedded in the HTTP request.
     *
     * @param serverName name of the server to connect to
     * @param serverPlatformURLRoot the network address of the server running the OMAS REST servers
     * @throws InvalidParameterException there is a problem creating the client-side components to issue any
     * REST API calls.
     */
    public AssetManagerClient(String serverName,
                              String serverPlatformURLRoot) throws InvalidParameterException
    {
        final String methodName = "Client Constructor";

        invalidParameterHandler.validateOMAGServerPlatformURL(serverPlatformURLRoot, serverName, methodName);

        this.serverName = serverName;
        this.serverPlatformURLRoot = serverPlatformURLRoot;
        
        this.restClient = new AssetManagerRESTClient(serverName, serverPlatformURLRoot);
    }


    /**
     * Create a new client that passes userId and password in each HTTP request.  This is the
     * userId/password of the calling server.  The end user's userId is sent on each request.
     *
     * @param serverName name of the server to connect to
     * @param serverPlatformURLRoot the network address of the server running the OMAS REST servers
     * @param userId caller's userId embedded in all HTTP requests
     * @param password caller's userId embedded in all HTTP requests
     * @throws InvalidParameterException there is a problem creating the client-side components to issue any
     * REST API calls.
     */
    public AssetManagerClient(String serverName,
                              String serverPlatformURLRoot,
                              String userId,
                              String password) throws InvalidParameterException
    {
        final String methodName = "Client Constructor";

        invalidParameterHandler.validateOMAGServerPlatformURL(serverPlatformURLRoot, serverName, methodName);

        this.serverName = serverName;
        this.serverPlatformURLRoot = serverPlatformURLRoot;
        this.restClient = new AssetManagerRESTClient(serverName, serverPlatformURLRoot, userId, password);
    }
    

    /**
     * Create a new client that passes userId and password in each HTTP request.  This is the
     * userId/password of the calling server.  The end user's userId is sent on each request.
     *
     * @param serverName name of the server to connect to
     * @param serverPlatformURLRoot the network address of the server running the OMAS REST servers
     * @param userId caller's userId embedded in all HTTP requests
     * @param password caller's userId embedded in all HTTP requests
     * @param auditLog logging destination
     *
     * @throws InvalidParameterException there is a problem creating the client-side components to issue any
     * REST API calls.
     */
    public AssetManagerClient(String   serverName,
                              String   serverPlatformURLRoot,
                              String   userId,
                              String   password,
                              AuditLog auditLog) throws InvalidParameterException
    {
        final String methodName = "Client Constructor";

        invalidParameterHandler.validateOMAGServerPlatformURL(serverPlatformURLRoot, serverName, methodName);

        this.serverName = serverName;
        this.serverPlatformURLRoot = serverPlatformURLRoot;

        this.restClient = new AssetManagerRESTClient(serverName, serverPlatformURLRoot, userId, password, auditLog);

        this.auditLog = auditLog;
    }

    

    /**
     * Create a new client that is to be used within an OMAG Server.
     *
     * @param serverName name of the server to connect to
     * @param serverPlatformURLRoot the network address of the server running the OMAS REST servers
     * @param restClient pre-initialized REST client
     * @param maxPageSize pre-initialized parameter limit
     * @param auditLog logging destination
     * @throws InvalidParameterException there is a problem with the information about the remote OMAS
     */
    public AssetManagerClient(String                 serverName,
                              String                 serverPlatformURLRoot,
                              AssetManagerRESTClient restClient,
                              int                    maxPageSize,
                              AuditLog               auditLog) throws InvalidParameterException
    {
        final String methodName = "Client Constructor";

        invalidParameterHandler.validateOMAGServerPlatformURL(serverPlatformURLRoot, serverName, methodName);
        invalidParameterHandler.setMaxPagingSize(maxPageSize);

        this.serverName = serverName;
        this.serverPlatformURLRoot = serverPlatformURLRoot;

        this.restClient = restClient;

        this.auditLog = auditLog;
    }


    /* ========================================================
     * The asset manager represents the third party technology this integration processing is connecting to
     */


    /**
     * Create information about the external asset manager.  This is represented as a software server capability
     * and all information that is specific to the external asset manager (such as the identifiers of the
     * metadata elements it stores) will be linked to it.
     *
     * @param userId calling user
     * @param assetManagerProperties description of the integration daemon (specify qualified name at a minimum)
     *
     * @return unique identifier of the asset management's software server capability
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException user not authorized to issue this request
     * @throws PropertyServerException    problem accessing the property server
     */
    public String createExternalAssetManager(String                 userId, 
                                             AssetManagerProperties assetManagerProperties) throws InvalidParameterException,
                                                                                                   UserNotAuthorizedException,
                                                                                                   PropertyServerException
    {
        final String methodName                  = "createExternalAssetManager";
        final String propertiesParameterName     = "assetManagerProperties";
        final String qualifiedNameParameterName  = "qualifiedName";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateObject(assetManagerProperties, propertiesParameterName, methodName);
        invalidParameterHandler.validateName(assetManagerProperties.getQualifiedName(), qualifiedNameParameterName, methodName);
        

        final String urlTemplate = serverPlatformURLRoot + urlTemplatePrefix;

        GUIDResponse restResult = restClient.callGUIDPostRESTCall(methodName,
                                                                  urlTemplate,
                                                                  assetManagerProperties,
                                                                  serverName,
                                                                  userId);

        return restResult.getGUID();
    }


    /**
     * Retrieve the unique identifier of the external asset manager from its qualified name.
     * Typically the qualified name comes from the integration connector configuration.
     *
     * @param userId calling user
     * @param qualifiedName unique name to use for the external asset
     *
     * @return unique identifier of the external asset manager's software server capability
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException user not authorized to issue this request
     * @throws PropertyServerException    problem accessing the property server
     */
    public String  getExternalAssetManagerGUID(String  userId,
                                               String  qualifiedName) throws InvalidParameterException,
                                                                             UserNotAuthorizedException,
                                                                             PropertyServerException
    {
        final String methodName                  = "getExternalAssetManagerGUID";
        final String qualifiedNameParameterName  = "qualifiedName";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateName(qualifiedName, qualifiedNameParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + urlTemplatePrefix + "/by-name/{2}";

        GUIDResponse restResult = restClient.callGUIDGetRESTCall(methodName,
                                                                 urlTemplate,
                                                                 serverName,
                                                                 userId,
                                                                 qualifiedName);

        return restResult.getGUID();
    }


    /**
     * Add a new external identifier to an existing open metadata element.
     *
     * @param userId calling user
     * @param assetManagerGUID unique identifier of software server capability representing the caller
     * @param assetManagerName unique name of software server capability representing the caller
     * @param synchronizationDirection details of the synchronization direction
     * @param synchronizationDescription optional additional description about the synchronization approach
     * @param openMetadataElementGUID unique identifier (GUID) of the element in the open metadata ecosystem
     * @param openMetadataElementTypeName type name for the open metadata element
     * @param externalIdentifier unique identifier of this element in the third party asset manager
     * @param externalIdentifierName name of the identifier in the third party asset manager
     * @param externalIdentifierUsage description of how the open metadata element maps to the identifier
     * @param externalIdentifierSource component that issuing this request.
     * @param keyPattern style of the external identifier
     * @param mappingProperties additional mapping properties
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException user not authorized to issue this request
     * @throws PropertyServerException    problem accessing the property server
     */
    public void addExternalIdentifier(String                   userId,
                                      String                   assetManagerGUID,
                                      String                   assetManagerName,
                                      SynchronizationDirection synchronizationDirection,
                                      String                   synchronizationDescription,
                                      String                   openMetadataElementGUID,
                                      String                   openMetadataElementTypeName,
                                      String                   externalIdentifier,
                                      String                   externalIdentifierName,
                                      String                   externalIdentifierUsage,
                                      String                   externalIdentifierSource,
                                      KeyPattern               keyPattern,
                                      Map<String, String>      mappingProperties) throws InvalidParameterException,
                                                                                         UserNotAuthorizedException,
                                                                                         PropertyServerException
    {
        final String methodName                      = "addExternalIdentifier";
        final String openMetadataGUIDParameterName   = "openMetadataElementGUID";
        final String openMetadataTypeParameterName   = "openMetadataElementTypeName";
        final String externalIdentifierParameterName = "externalIdentifier";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(assetManagerGUID, assetManagerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(assetManagerName, assetManagerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(openMetadataElementGUID, openMetadataGUIDParameterName, methodName);
        invalidParameterHandler.validateName(openMetadataElementTypeName, openMetadataTypeParameterName, methodName);
        invalidParameterHandler.validateGUID(externalIdentifier, externalIdentifierParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + urlTemplatePrefix + "/elements/{2}/{3}/external-identifiers/add";

        MetadataCorrelationProperties requestBody = new MetadataCorrelationProperties();

        requestBody.setAssetManagerGUID(assetManagerGUID);
        requestBody.setAssetManagerName(assetManagerName);
        requestBody.setSynchronizationDirection(synchronizationDirection);
        requestBody.setSynchronizationDescription(synchronizationDescription);
        requestBody.setExternalIdentifier(externalIdentifier);
        requestBody.setExternalIdentifierName(externalIdentifierName);
        requestBody.setExternalIdentifierUsage(externalIdentifierUsage);
        requestBody.setExternalIdentifierSource(externalIdentifierSource);
        requestBody.setKeyPattern(keyPattern);
        requestBody.setMappingProperties(mappingProperties);

        restClient.callVoidPostRESTCall(methodName,
                                        urlTemplate,
                                        requestBody,
                                        serverName,
                                        userId,
                                        openMetadataElementTypeName,
                                        openMetadataElementGUID);
    }


    /**
     * Update the description of a specific external identifier.
     *
     * @param userId calling user
     * @param assetManagerGUID unique identifier of software server capability representing the caller
     * @param assetManagerName unique name of software server capability representing the caller
     * @param synchronizationDirection details of the synchronization direction
     * @param synchronizationDescription optional additional description about the synchronization approach
     * @param openMetadataElementGUID unique identifier (GUID) of the element in the open metadata ecosystem
     * @param openMetadataElementTypeName type name for the open metadata element
     * @param externalIdentifier unique identifier of this element in the third party asset manager
     * @param externalIdentifierName name of the identifier in the third party asset manager
     * @param externalIdentifierUsage description of how the open metadata element maps to the identifier
     * @param externalIdentifierSource component that issuing this request.
     * @param keyPattern style of the external identifier
     * @param mappingProperties additional mapping properties
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException user not authorized to issue this request
     * @throws PropertyServerException    problem accessing the property server
     */
    public void updateExternalIdentifier(String                   userId,
                                         String                   assetManagerGUID,
                                         String                   assetManagerName,
                                         SynchronizationDirection synchronizationDirection,
                                         String                   synchronizationDescription,
                                         String                   openMetadataElementGUID,
                                         String                   openMetadataElementTypeName,
                                         String                   externalIdentifier,
                                         String                   externalIdentifierName,
                                         String                   externalIdentifierUsage,
                                         String                   externalIdentifierSource,
                                         KeyPattern               keyPattern,
                                         Map<String, String>      mappingProperties) throws InvalidParameterException,
                                                                                            UserNotAuthorizedException,
                                                                                            PropertyServerException
    {
        final String methodName                      = "updateExternalIdentifier";
        final String openMetadataGUIDParameterName   = "openMetadataElementGUID";
        final String openMetadataTypeParameterName   = "openMetadataElementTypeName";
        final String externalIdentifierParameterName = "externalIdentifier";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(assetManagerGUID, assetManagerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(assetManagerName, assetManagerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(openMetadataElementGUID, openMetadataGUIDParameterName, methodName);
        invalidParameterHandler.validateName(openMetadataElementTypeName, openMetadataTypeParameterName, methodName);
        invalidParameterHandler.validateGUID(externalIdentifier, externalIdentifierParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + urlTemplatePrefix + "/elements/{2}/{3}/external-identifiers/update";

        MetadataCorrelationProperties requestBody = new MetadataCorrelationProperties();

        requestBody.setAssetManagerGUID(assetManagerGUID);
        requestBody.setAssetManagerName(assetManagerName);
        requestBody.setSynchronizationDirection(synchronizationDirection);
        requestBody.setSynchronizationDescription(synchronizationDescription);
        requestBody.setExternalIdentifier(externalIdentifier);
        requestBody.setExternalIdentifierName(externalIdentifierName);
        requestBody.setExternalIdentifierUsage(externalIdentifierUsage);
        requestBody.setExternalIdentifierSource(externalIdentifierSource);
        requestBody.setKeyPattern(keyPattern);
        requestBody.setMappingProperties(mappingProperties);

        restClient.callVoidPostRESTCall(methodName,
                                        urlTemplate,
                                        requestBody,
                                        serverName,
                                        userId,
                                        openMetadataElementTypeName,
                                        openMetadataElementGUID);
    }


    /**
     * Remove an external identifier from an existing open metadata element.  The open metadata element is not
     * affected.
     *
     * @param userId calling user
     * @param assetManagerGUID unique identifier of software server capability representing the caller
     * @param assetManagerName unique name of software server capability representing the caller
     * @param synchronizationDirection details of the synchronization direction
     * @param synchronizationDescription optional additional description about the synchronization approach
     * @param openMetadataElementGUID unique identifier (GUID) of the element in the open metadata ecosystem
     * @param openMetadataElementTypeName type name for the open metadata element
     * @param externalIdentifier unique identifier of this element in the third party asset manager
     * @param externalIdentifierName name of the identifier in the third party asset manager
     * @param externalIdentifierUsage description of how the open metadata element maps to the identifier
     * @param externalIdentifierSource component that issuing this request.
     * @param keyPattern style of the external identifier
     * @param mappingProperties additional mapping properties
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException user not authorized to issue this request
     * @throws PropertyServerException    problem accessing the property server
     */
    public void removeExternalIdentifier(String                   userId,
                                         String                   assetManagerGUID,
                                         String                   assetManagerName,
                                         SynchronizationDirection synchronizationDirection,
                                         String                   synchronizationDescription,
                                         String                   openMetadataElementGUID,
                                         String                   openMetadataElementTypeName,
                                         String                   externalIdentifier,
                                         String                   externalIdentifierName,
                                         String                   externalIdentifierUsage,
                                         String                   externalIdentifierSource,
                                         KeyPattern               keyPattern,
                                         Map<String, String>      mappingProperties) throws InvalidParameterException,
                                                                                            UserNotAuthorizedException,
                                                                                            PropertyServerException
    {
        final String methodName                      = "removeExternalIdentifier";
        final String openMetadataGUIDParameterName   = "openMetadataElementGUID";
        final String openMetadataTypeParameterName   = "openMetadataElementTypeName";
        final String externalIdentifierParameterName = "externalIdentifier";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(assetManagerGUID, assetManagerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(assetManagerName, assetManagerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(openMetadataElementGUID, openMetadataGUIDParameterName, methodName);
        invalidParameterHandler.validateName(openMetadataElementTypeName, openMetadataTypeParameterName, methodName);
        invalidParameterHandler.validateGUID(externalIdentifier, externalIdentifierParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + urlTemplatePrefix + "/elements/{2}/{3}/external-identifiers/remove";

        MetadataCorrelationProperties requestBody = new MetadataCorrelationProperties();

        requestBody.setAssetManagerGUID(assetManagerGUID);
        requestBody.setAssetManagerName(assetManagerName);
        requestBody.setSynchronizationDirection(synchronizationDirection);
        requestBody.setSynchronizationDescription(synchronizationDescription);
        requestBody.setExternalIdentifier(externalIdentifier);
        requestBody.setExternalIdentifierName(externalIdentifierName);
        requestBody.setExternalIdentifierUsage(externalIdentifierUsage);
        requestBody.setExternalIdentifierSource(externalIdentifierSource);
        requestBody.setKeyPattern(keyPattern);
        requestBody.setMappingProperties(mappingProperties);

        restClient.callVoidPostRESTCall(methodName,
                                        urlTemplate,
                                        requestBody,
                                        serverName,
                                        userId,
                                        openMetadataElementTypeName,
                                        openMetadataElementGUID);
    }


    /**
     * Confirm that the values of a particular metadata element have been synchronized.  This is important
     * from an audit point of view, and to allow bidirectional updates of metadata using optimistic locking.
     *
     * @param userId calling user
     * @param assetManagerGUID unique identifier of software server capability representing the caller
     * @param assetManagerName unique name of software server capability representing the caller
     * @param openMetadataElementGUID unique identifier (GUID) of this element in open metadata
     * @param openMetadataElementTypeName type name for the open metadata element
     * @param externalIdentifier unique identifier of this element in the external asset manager
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException user not authorized to issue this request
     * @throws PropertyServerException    problem accessing the property server
     */
    public void confirmSynchronization(String userId,
                                       String assetManagerGUID,
                                       String assetManagerName,
                                       String openMetadataElementGUID,
                                       String openMetadataElementTypeName,
                                       String externalIdentifier) throws InvalidParameterException,
                                                                         UserNotAuthorizedException,
                                                                         PropertyServerException
    {
        final String methodName                      = "confirmSynchronization";
        final String openMetadataGUIDParameterName   = "openMetadataElementGUID";
        final String externalIdentifierParameterName = "externalIdentifier";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(assetManagerGUID, assetManagerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(assetManagerName, assetManagerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(openMetadataElementGUID, openMetadataGUIDParameterName, methodName);
        invalidParameterHandler.validateGUID(externalIdentifier, externalIdentifierParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + urlTemplatePrefix + "/elements/{2}/{3}/synchronized";

        MetadataCorrelationProperties requestBody = new MetadataCorrelationProperties();

        requestBody.setAssetManagerGUID(assetManagerGUID);
        requestBody.setAssetManagerName(assetManagerName);
        requestBody.setExternalIdentifier(externalIdentifier);

        restClient.callVoidPostRESTCall(methodName,
                                        urlTemplate,
                                        requestBody,
                                        serverName,
                                        userId,
                                        openMetadataElementGUID,
                                        openMetadataElementTypeName);
    }


    /**
     * Return the list of headers for open metadata elements that are associated with a particular
     * external identifier.
     *
     * @param userId calling user
     * @param assetManagerGUID unique identifier of software server capability representing the caller
     * @param assetManagerName unique name of software server capability representing the caller
     * @param externalIdentifier unique identifier of this element in the external asset manager
     * @param startFrom paging start point
     * @param pageSize maximum results that can be returned
     *
     * @return list of element headers
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException user not authorized to issue this request
     * @throws PropertyServerException    problem accessing the property server
     */
    public List<ElementHeader> getElementsForExternalIdentifier(String userId,
                                                                String assetManagerGUID,
                                                                String assetManagerName,
                                                                String externalIdentifier,
                                                                int    startFrom,
                                                                int    pageSize) throws InvalidParameterException,
                                                                                        UserNotAuthorizedException,
                                                                                        PropertyServerException
    {
        final String methodName                      = "getElementsForExternalIdentifier";
        final String externalIdentifierParameterName = "externalIdentifier";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(assetManagerGUID, assetManagerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(assetManagerName, assetManagerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(externalIdentifier, externalIdentifierParameterName, methodName);
        int validatedPageSize = invalidParameterHandler.validatePaging(startFrom, pageSize, methodName);

        final String urlTemplate = serverPlatformURLRoot + urlTemplatePrefix + "/external-identifiers/open-metadata-elements?startFrom={2}&pageSize={3}";

        MetadataCorrelationProperties requestBody = new MetadataCorrelationProperties();

        requestBody.setAssetManagerGUID(assetManagerGUID);
        requestBody.setAssetManagerName(assetManagerName);
        requestBody.setExternalIdentifier(externalIdentifier);

        ElementHeadersResponse restResult = restClient.callElementHeadersPostRESTCall(methodName,
                                                                                      urlTemplate,
                                                                                      requestBody,
                                                                                      serverName,
                                                                                      userId,
                                                                                      startFrom,
                                                                                      validatedPageSize);

        return restResult.getElementList();
    }
}
