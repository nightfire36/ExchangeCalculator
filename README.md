# ExchangeCalculator

Api documentation.

Application runs on port 8080 and provides following services:
All services are available by GET HTTP request.

GET /api/available_rates
Lists all available currencies beetween which exchange rates and amounts can be calculated.
Response has form od three letters strings array.

GET /api/all_rates
Sends exchange rates of all available currencies in relation to PLN.
Response has form of JSON array in which each element has two fields:
currencyPair - six letter string determining currency pair
rate - floating point number determining exchange rate

GET /api/rate/{currency1}/{currency2}
Sends exchange rate of any currency pair choosen form available currencies.
Example request: /api/rate/EUR/CAD
Response is a floating point number.

GET /api/calculate/{currency1}/{currency2}/{amount}
Sends amount of {currency2} that one would get in exchange for provided {amount} of {currency1}
Example request: /api/calculate/GBP/AUD/400000.5
Response is a floating point number.
