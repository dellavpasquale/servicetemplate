# Documentation for PurchaseOrder API

<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *https://purchaseorder/api*

| Class | Method | HTTP request | Description |
|------------ | ------------- | ------------- | -------------|
| *PurchaseorderApi* | [**createPurchaseOrder**](Apis/PurchaseorderApi.md#createpurchaseorder) | **POST** /purchaseorder | Create a new PurchaseOrder |
*PurchaseorderApi* | [**createPurchaseOrderTransition**](Apis/PurchaseorderApi.md#createpurchaseordertransition) | **POST** /purchaseorder/{purchaseorderCode}/transition | Create a new status transition |
*PurchaseorderApi* | [**getPurchaseOrderByCode**](Apis/PurchaseorderApi.md#getpurchaseorderbycode) | **GET** /purchaseorder/{purchaseorderCode} | Find a PurchaseOrder |
*PurchaseorderApi* | [**updatePurchaseOrder**](Apis/PurchaseorderApi.md#updatepurchaseorder) | **PUT** /purchaseorder | Update an existing PurchaseOrder |


<a name="documentation-for-models"></a>
## Documentation for Models

 - [Problem](./Models/Problem.md)
 - [PurchaseOrder](./Models/PurchaseOrder.md)
 - [PurchaseOrderRequest](./Models/PurchaseOrderRequest.md)
 - [PurchaseOrderTransitionRequest](./Models/PurchaseOrderTransitionRequest.md)
 - [PurchaseOrderUpdateRequest](./Models/PurchaseOrderUpdateRequest.md)


<a name="documentation-for-authorization"></a>
## Documentation for Authorization

All endpoints do not require authorization.
