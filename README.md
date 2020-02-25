# ExchangeCalculator
<br /><br />
Api documentation.<br /><br />

Application runs on port 8080 and provides following services:<br />
All services are available by GET HTTP request.<br /><br />

GET /api/available_rates<br />
Lists all available currencies beetween which exchange rates and amounts can be calculated.<br />
Response has form od three letters strings array.<br /><br />

GET /api/all_rates<br />
Sends exchange rates of all available currencies in relation to PLN.<br />
Response has form of JSON array in which each element has two fields:<br />
currencyPair - six letter string determining currency pair<br />
rate - floating point number determining exchange rate<br /><br />

GET /api/rate/{currency1}/{currency2}<br />
Sends exchange rate of any currency pair choosen form available currencies.<br />
Example request: /api/rate/EUR/CAD<br />
Response is a floating point number.<br /><br />

GET /api/calculate/{currency1}/{currency2}/{amount}<br />
Sends amount of {currency2} that one would get in exchange for provided {amount} of {currency1}<br />
Example request: /api/calculate/GBP/AUD/400000.5<br />
Response is a floating point number.<br />
