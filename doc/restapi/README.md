# PurchaseOrder API

This is the API to manage PurchaseOrder objects <br><br>Audience: <i>company-internal</i>  

## Info 

  

*Version:* 1.0.0  
*BasePath:* /api  
  
  


  
[Find out more about PurchaseOrder component](http://swagger.io)

## Security

*Not defined*
## Endpoints

  

### createPurchaseOrder

> PurchaseOrder createPurchaseOrder(PurchaseOrderRequest)

*Summary:* Create a new PurchaseOrder   
*Description:* Create a new PurchaseOrder   

**HttpMethod:** POST  
**Protocol:** https  
**Path:** /purchaseorder  
**Produces:** application/json    
**Consumes:** application/json 



#### Parameters



##### Body Parameter

| Name | Description | Type | Format | Required | Nullable | Default | Constraints |
| --- | --- | --- | --- | --- | --- | --- | --- |
|PurchaseOrderRequest | Create a new PurchaseOrder | PurchaseOrderRequest |  | yes | no |  |  |





#### Responses

| Status Code | Message | Return Type | Example |
| --- | --- | --- | --- |
| 200 | Successful operation | PurchaseOrder |  |
| 400 | Invalid request supplied | Problem |  |
| 500 | Internal error | Problem |  |

#### Example

##### Request

```bash
curl -X  \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
"https://purchaseorder/api/purchaseorder" \
-d '{
      "code" : "e9dc6700-9842-40db-9e60-9e7044a3791a",
      "customer" : "John Mitch"
    }'

```

##### Response

```json
{
  "orderedAt" : "2000-01-23T04:56:07.000+00:00",
  "createdAt" : "2000-01-23T04:56:07.000+00:00",
  "product" : "Water",
  "amount" : 99.95,
  "code" : "e9dc6700-9842-40db-9e60-9e7044a3791a",
  "status" : "DRAFT",
  "customer" : "John Mitch",
  "expectedDeliveryAt" : "2000-01-23T04:56:07.000+00:00"
}
```

  

### createPurchaseOrderTransition

> PurchaseOrder createPurchaseOrderTransition(purchaseorderCode, PurchaseOrderTransitionRequest)

*Summary:* Create a new status transition   
*Description:* Create a new status transition   

**HttpMethod:** POST  
**Protocol:** https  
**Path:** /purchaseorder/{purchaseorderCode}/transition  
**Produces:** application/json    
**Consumes:** application/json 



#### Parameters


##### Path Parameters

| Name | Description | Type | Format | Required | Nullable | Default | Constraints |
| --- | --- | --- | --- | --- | --- | --- | --- |
|purchaseorderCode | PurchaseOrder code | String |  | yes | no | null | [Length(8,40)] |



##### Body Parameter

| Name | Description | Type | Format | Required | Nullable | Default | Constraints |
| --- | --- | --- | --- | --- | --- | --- | --- |
|PurchaseOrderTransitionRequest | Create a new status transition | PurchaseOrderTransitionRequest |  | yes | no |  |  |





#### Responses

| Status Code | Message | Return Type | Example |
| --- | --- | --- | --- |
| 200 | Successful operation | PurchaseOrder |  |
| 400 | Invalid request supplied | Problem |  |
| 500 | Internal error | Problem |  |

#### Example

##### Request

```bash
curl -X  \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
"https://purchaseorder/api/purchaseorder/{purchaseorderCode}/transition" \
-d '{
      "transition" : "SEND",
      "expectedDeliveryAt" : "2000-01-23T04:56:07.000+00:00"
    }'

```
where:  
 - `{purchaseorderCode}` -> `e9dc6700-9842-40db-9e60-9e7044a3791a`

##### Response

```json
{
  "orderedAt" : "2000-01-23T04:56:07.000+00:00",
  "createdAt" : "2000-01-23T04:56:07.000+00:00",
  "product" : "Water",
  "amount" : 99.95,
  "code" : "e9dc6700-9842-40db-9e60-9e7044a3791a",
  "status" : "DRAFT",
  "customer" : "John Mitch",
  "expectedDeliveryAt" : "2000-01-23T04:56:07.000+00:00"
}
```

  

### getPurchaseOrderByCode

> PurchaseOrder getPurchaseOrderByCode(purchaseorderCode)

*Summary:* Find a PurchaseOrder   
*Description:* Find a Purchase Order by code   

**HttpMethod:** GET  
**Protocol:** https  
**Path:** /purchaseorder/{purchaseorderCode}  
**Produces:** application/json    




#### Parameters


##### Path Parameters

| Name | Description | Type | Format | Required | Nullable | Default | Constraints |
| --- | --- | --- | --- | --- | --- | --- | --- |
|purchaseorderCode | PurchaseOrder code | String |  | yes | no | null | [Length(8,40)] |






#### Responses

| Status Code | Message | Return Type | Example |
| --- | --- | --- | --- |
| 200 | successful operation | PurchaseOrder |  |
| 400 | Invalid request supplied | Problem |  |
| 404 | PurchaseOrder not found | Problem |  |
| 500 | Internal error | Problem |  |

#### Example

##### Request

```bash
curl -X  \
-H "Accept: application/json" \
"https://purchaseorder/api/purchaseorder/{purchaseorderCode}"
```
where:  
 - `{purchaseorderCode}` -> `e9dc6700-9842-40db-9e60-9e7044a3791a`

##### Response

```json
{
  "orderedAt" : "2000-01-23T04:56:07.000+00:00",
  "createdAt" : "2000-01-23T04:56:07.000+00:00",
  "product" : "Water",
  "amount" : 99.95,
  "code" : "e9dc6700-9842-40db-9e60-9e7044a3791a",
  "status" : "DRAFT",
  "customer" : "John Mitch",
  "expectedDeliveryAt" : "2000-01-23T04:56:07.000+00:00"
}
```

  

### updatePurchaseOrder

> PurchaseOrder updatePurchaseOrder(PurchaseOrderUpdateRequest)

*Summary:* Update an existing PurchaseOrder   
*Description:* Update an existing PurchaseOrder by Id   

**HttpMethod:** PUT  
**Protocol:** https  
**Path:** /purchaseorder  
**Produces:** application/json    
**Consumes:** application/json 



#### Parameters



##### Body Parameter

| Name | Description | Type | Format | Required | Nullable | Default | Constraints |
| --- | --- | --- | --- | --- | --- | --- | --- |
|PurchaseOrderUpdateRequest | Update an existing PurchaseOrder | PurchaseOrderUpdateRequest |  | yes | no |  |  |





#### Responses

| Status Code | Message | Return Type | Example |
| --- | --- | --- | --- |
| 200 | Successful operation | PurchaseOrder |  |
| 400 | Invalid request supplied | Problem |  |
| 404 | PurchaseOrder not found | Problem |  |
| 500 | Internal error | Problem |  |

#### Example

##### Request

```bash
curl -X  \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
"https://purchaseorder/api/purchaseorder" \
-d '{
      "product" : "Water",
      "amount" : 99.95,
      "code" : "e9dc6700-9842-40db-9e60-9e7044a3791a"
    }'

```

##### Response

```json
{
  "orderedAt" : "2000-01-23T04:56:07.000+00:00",
  "createdAt" : "2000-01-23T04:56:07.000+00:00",
  "product" : "Water",
  "amount" : 99.95,
  "code" : "e9dc6700-9842-40db-9e60-9e7044a3791a",
  "status" : "DRAFT",
  "customer" : "John Mitch",
  "expectedDeliveryAt" : "2000-01-23T04:56:07.000+00:00"
}
```


## Model


### Problem



| Field Name | Description | Type | Format | Required | Nullable | Default | Constraints |
| --- | --- | --- | --- | --- | --- | --- | --- |
|type | A URI reference that uniquely identifies the problem type only in the context of the provided API. Opposed to the specification in RFC-7807, it is neither recommended to be dereferenceable and point to a human-readable documentation nor globally unique for the problem type.  | String | uri-reference | no | no | about:blank |  |
|title | A short summary of the problem type. Written in English and readable for engineers, usually not suited for non technical stakeholders and not localized.  | String |  | no | no | null |  |
|status | The HTTP status code generated by the origin server for this occurrence of the problem.  | Integer | int32 | no | no | null | [Range(100,lt 600)] |
|detail | A human readable explanation specific to this occurrence of the problem that is helpful to locate the problem and give advice on how to proceed. Written in English and readable for engineers, usually not suited for non technical stakeholders and not localized.  | String |  | no | no | null |  |
|instance | A URI reference that identifies the specific occurrence of the problem, e.g. by adding a fragment identifier or sub-path to the problem type. May be used to locate the root of this problem in the source code.  | String | uri-reference | no | no | null |  |
 

### PurchaseOrder



| Field Name | Description | Type | Format | Required | Nullable | Default | Constraints |
| --- | --- | --- | --- | --- | --- | --- | --- |
|code |  | String |  | yes | no | null | [Length(8,40)] |
|status |  | String |  | yes | no | null | [DRAFT, ORDERED, CANCELLED, IN_PROGRESS, SENT, DELIVERED] |
|customer |  | String |  | yes | no | null | [Length(2,56)] |
|product |  | String |  | no | no | null | [Length(2,56)] |
|amount |  | BigDecimal | decimal | no | no | null |  |
|createdAt |  | Date | date-time | no | no | null |  |
|orderedAt |  | Date | date-time | no | no | null |  |
|expectedDeliveryAt |  | Date | date-time | no | no | null |  |
 

### PurchaseOrderRequest



| Field Name | Description | Type | Format | Required | Nullable | Default | Constraints |
| --- | --- | --- | --- | --- | --- | --- | --- |
|code |  | String |  | no | no | null | [Length(8,40)] |
|customer |  | String |  | yes | no | null | [Length(2,56)] |
 

### PurchaseOrderTransitionRequest



| Field Name | Description | Type | Format | Required | Nullable | Default | Constraints |
| --- | --- | --- | --- | --- | --- | --- | --- |
|transition |  | String |  | yes | no | null | [ORDER, CANCEL, START_TO_WORK, SEND, DELIVER] |
|expectedDeliveryAt |  | Date | date-time | no | no | null |  |
 

### PurchaseOrderUpdateRequest



| Field Name | Description | Type | Format | Required | Nullable | Default | Constraints |
| --- | --- | --- | --- | --- | --- | --- | --- |
|code |  | String |  | yes | no | null | [Length(8,40)] |
|product |  | String |  | yes | no | null | [Length(2,56)] |
|amount |  | BigDecimal | decimal | no | no | null |  |
 
