# PurchaseOrder API

This is the API to manage PurchaseOrder objects  

## Info 

  

*Version:* 1.0.0  
*BasePath:* /api  
  
  

## Access


## Endpoints

  

### createPurchaseOrder

*Summary:* Create a new PurchaseOrder   
*Description:* Create a new PurchaseOrder   

**HttpMethod:** POST  
**Protocol:** https  
**Path:** /purchaseorder  
**Produces:** application/json    

#### Parameters



##### Body Parameter

| Name | Description | Type | Required | Default | Pattern | Enum |
| --- | --- | --- | --- | --- | --- | --- |
|PurchaseOrderRequest | Create a new PurchaseOrder | [PurchaseOrderRequest](#PurchaseOrderRequest) | yes |  |  |  |



##### Return type
<a href="#PurchaseOrder">PurchaseOrder</a>




#### Responses

| Status Code | Message | Return Type | Example |
| --- | --- | --- |
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

*Summary:* Create a new status transition   
*Description:* Create a new status transition   

**HttpMethod:** POST  
**Protocol:** https  
**Path:** /purchaseorder/{purchaseorderCode}/transition  
**Produces:** application/json    

#### Parameters


##### Path Parameters

| Name | Description | Type | Required | Default | Pattern | Enum |
| --- | --- | --- | --- | --- | --- | --- |
|purchaseorderCode | PurchaseOrder code | String | yes | null | [StringLength(40, MinimumLength=8)] |  |



##### Body Parameter

| Name | Description | Type | Required | Default | Pattern | Enum |
| --- | --- | --- | --- | --- | --- | --- |
|PurchaseOrderTransitionRequest | Create a new status transition | [PurchaseOrderTransitionRequest](#PurchaseOrderTransitionRequest) | yes |  |  |  |



##### Return type
<a href="#PurchaseOrder">PurchaseOrder</a>




#### Responses

| Status Code | Message | Return Type | Example |
| --- | --- | --- |
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

*Summary:* Find a PurchaseOrder   
*Description:* Find a Purchase Order by code   

**HttpMethod:** GET  
**Protocol:** https  
**Path:** /purchaseorder/{purchaseorderCode}  
**Produces:** application/json    

#### Parameters


##### Path Parameters

| Name | Description | Type | Required | Default | Pattern | Enum |
| --- | --- | --- | --- | --- | --- | --- |
|purchaseorderCode | PurchaseOrder code | String | yes | null | [StringLength(40, MinimumLength=8)] |  |




##### Return type
<a href="#PurchaseOrder">PurchaseOrder</a>




#### Responses

| Status Code | Message | Return Type | Example |
| --- | --- | --- |
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

*Summary:* Update an existing PurchaseOrder   
*Description:* Update an existing PurchaseOrder by Id   

**HttpMethod:** PUT  
**Protocol:** https  
**Path:** /purchaseorder  
**Produces:** application/json    

#### Parameters



##### Body Parameter

| Name | Description | Type | Required | Default | Pattern | Enum |
| --- | --- | --- | --- | --- | --- | --- |
|PurchaseOrderUpdateRequest | Update an existing PurchaseOrder | [PurchaseOrderUpdateRequest](#PurchaseOrderUpdateRequest) | yes |  |  |  |



##### Return type
<a href="#PurchaseOrder">PurchaseOrder</a>




#### Responses

| Status Code | Message | Return Type | Example |
| --- | --- | --- |
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



| Field Name | Description | Type | Required | Default | Pattern | Enum | Test
| --- | --- | --- | --- | --- | --- | --- |
|type | A URI reference that uniquely identifies the problem type only in the context of the provided API. Opposed to the specification in RFC-7807, it is neither recommended to be dereferenceable and point to a human-readable documentation nor globally unique for the problem type.  | String | no | about:blank |  |  |
 </span> A URI reference that uniquely identifies the problem type only in the context of the provided API. Opposed to the specification in RFC-7807, it is neither recommended to be dereferenceable and point to a human-readable documentation nor globally unique for the problem type.
 format: uri-reference | |title | A short summary of the problem type. Written in English and readable for engineers, usually not suited for non technical stakeholders and not localized.  | String | no | null |  |  |
 </span> A short summary of the problem type. Written in English and readable for engineers, usually not suited for non technical stakeholders and not localized.
  | |status | The HTTP status code generated by the origin server for this occurrence of the problem.  | Integer | no | null | [Range(100, 600)] |  |
 </span> The HTTP status code generated by the origin server for this occurrence of the problem.
 format: int32 | |detail | A human readable explanation specific to this occurrence of the problem that is helpful to locate the problem and give advice on how to proceed. Written in English and readable for engineers, usually not suited for non technical stakeholders and not localized.  | String | no | null |  |  |
 </span> A human readable explanation specific to this occurrence of the problem that is helpful to locate the problem and give advice on how to proceed. Written in English and readable for engineers, usually not suited for non technical stakeholders and not localized.
  | |instance | A URI reference that identifies the specific occurrence of the problem, e.g. by adding a fragment identifier or sub-path to the problem type. May be used to locate the root of this problem in the source code.  | String | no | null |  |  |
 </span> A URI reference that identifies the specific occurrence of the problem, e.g. by adding a fragment identifier or sub-path to the problem type. May be used to locate the root of this problem in the source code.
 format: uri-reference |  

### PurchaseOrder



| Field Name | Description | Type | Required | Default | Pattern | Enum | Test
| --- | --- | --- | --- | --- | --- | --- |
|code |  | String | yes | null | [StringLength(40, MinimumLength=8)] |  |
 </span>   | |status |  | String | yes | null |  | [DRAFT, ORDERED, CANCELLED, IN_PROGRESS, SENT, DELIVERED] |
 </span>   | |customer |  | String | yes | null | [StringLength(56, MinimumLength=2)] |  |
 </span>   | |product |  | String | no | null | [StringLength(56, MinimumLength=2)] |  |
 </span>   | |amount |  | BigDecimal | no | null |  |  |
 </span>  format: decimal | |createdAt |  | Date | no | null |  |  |
 </span>  format: date-time | |orderedAt |  | Date | no | null |  |  |
 </span>  format: date-time | |expectedDeliveryAt |  | Date | no | null |  |  |
 </span>  format: date-time |  

### PurchaseOrderRequest



| Field Name | Description | Type | Required | Default | Pattern | Enum | Test
| --- | --- | --- | --- | --- | --- | --- |
|code |  | String | no | null | [StringLength(40, MinimumLength=8)] |  |
 </span>   | |customer |  | String | yes | null | [StringLength(56, MinimumLength=2)] |  |
 </span>   |  

### PurchaseOrderTransitionRequest



| Field Name | Description | Type | Required | Default | Pattern | Enum | Test
| --- | --- | --- | --- | --- | --- | --- |
|transition |  | String | yes | null |  | [ORDER, CANCEL, START_TO_WORK, SEND, DELIVER] |
 </span>   | |expectedDeliveryAt |  | Date | no | null |  |  |
 </span>  format: date-time |  

### PurchaseOrderUpdateRequest



| Field Name | Description | Type | Required | Default | Pattern | Enum | Test
| --- | --- | --- | --- | --- | --- | --- |
|code |  | String | yes | null | [StringLength(40, MinimumLength=8)] |  |
 </span>   | |product |  | String | yes | null | [StringLength(56, MinimumLength=2)] |  |
 </span>   | |amount |  | BigDecimal | no | null |  |  |
 </span>  format: decimal |  
