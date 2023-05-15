# Design Rules

## Domain
1. SHOULD define DomainEntity aggregates based on transactions
1. COULD have different DomainEntity for different Actors that operate on an attribute subset
1. MUST not use any framework to define DomainEntity
1. MUST use BigDecimal class for decimal numbers to define DomainEntity
1. MUST use java.time classes for date, time and period to define DomainEntity
1. MUST NOT use primitive types to define DomainEntity
1. MUST validate the usecase request not the output
1. MUST check access control into domain

 