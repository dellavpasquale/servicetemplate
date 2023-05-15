# PurchaseorderApi

All URIs are relative to *https://purchaseorder/api*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createPurchaseOrder**](PurchaseorderApi.md#createPurchaseOrder) | **POST** /purchaseorder | Create a new PurchaseOrder |
| [**createPurchaseOrderTransition**](PurchaseorderApi.md#createPurchaseOrderTransition) | **POST** /purchaseorder/{purchaseorderCode}/transition | Create a new status transition |
| [**getPurchaseOrderByCode**](PurchaseorderApi.md#getPurchaseOrderByCode) | **GET** /purchaseorder/{purchaseorderCode} | Find a PurchaseOrder |
| [**updatePurchaseOrder**](PurchaseorderApi.md#updatePurchaseOrder) | **PUT** /purchaseorder | Update an existing PurchaseOrder |


<a name="createPurchaseOrder"></a>
# **createPurchaseOrder**
> PurchaseOrder createPurchaseOrder(PurchaseOrderRequest)

Create a new PurchaseOrder

    Create a new PurchaseOrder

### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **PurchaseOrderRequest** | [**PurchaseOrderRequest**](../Models/PurchaseOrderRequest.md)| Create a new PurchaseOrder | |

### Return type

[**PurchaseOrder**](../Models/PurchaseOrder.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json, application/problem+json

<a name="createPurchaseOrderTransition"></a>
# **createPurchaseOrderTransition**
> PurchaseOrder createPurchaseOrderTransition(purchaseorderCode, PurchaseOrderTransitionRequest)

Create a new status transition

    Create a new status transition

### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **purchaseorderCode** | **String**| PurchaseOrder code | [default to null] |
| **PurchaseOrderTransitionRequest** | [**PurchaseOrderTransitionRequest**](../Models/PurchaseOrderTransitionRequest.md)| Create a new status transition | |

### Return type

[**PurchaseOrder**](../Models/PurchaseOrder.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json, application/problem+json

<a name="getPurchaseOrderByCode"></a>
# **getPurchaseOrderByCode**
> PurchaseOrder getPurchaseOrderByCode(purchaseorderCode)

Find a PurchaseOrder

    Find a Purchase Order by code

### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **purchaseorderCode** | **String**| PurchaseOrder code | [default to null] |

### Return type

[**PurchaseOrder**](../Models/PurchaseOrder.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, application/problem+json

<a name="updatePurchaseOrder"></a>
# **updatePurchaseOrder**
> PurchaseOrder updatePurchaseOrder(PurchaseOrderUpdateRequest)

Update an existing PurchaseOrder

    Update an existing PurchaseOrder by Id

### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **PurchaseOrderUpdateRequest** | [**PurchaseOrderUpdateRequest**](../Models/PurchaseOrderUpdateRequest.md)| Update an existing PurchaseOrder | |

### Return type

[**PurchaseOrder**](../Models/PurchaseOrder.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json, application/problem+json

