Unified strings files for all platforms. 

Currently there are a few translations missing.

Note that there is a confusion around swedbankpaysdk_problem_unauthorized and swedbankpaysdk_problem_invalid_request. They are different in the SDK but the translation is the same.


| Key | Description | English | Norwegian (bokmål) | Swedish |
| --- | ----------- | ------- | ------------------ | ------- |
| swedbankpaysdk_error_dialog_title | A general error title | Error | Error | Error |
| swedbankpaysdk_dialog_close | Close button label | Close | Lukk | Stäng |
| OK | General ok message | OK | OK | OK |
| Cancel | General cancellation | Cancel | Avbryt | Avbryt |
| browserAlertTitle | Description missing | You're almost done! | Du er nesten ferdig! | Du är snart klar! |
| browserAlertBody | Description missing | Your payment is being processed. Return to your browser to continue. | Betalingen din behandles. Gå tilbake til nettleseren for å fortsette. | Din betalning bearbetas. Gå tillbaka till webbläsaren för att fortsätta. |
| maybeStuckAlertTitle | The SDK detecting/guessing that a payment is stuck. | Stuck? | Kommer du ikke videre? | Kommer du inte vidare? |
| maybeStuckAlertBody | The SDK defaults to opening “unknown” payment flows in the browser, in most cases the best UX is accomplished by staying in the web view. To stay in the web view as often as possible, the payment flow will initially stay in the web view, but if the SDK suspects that the process may have got stuck, it will alert the user and allow them to retry the payment.. Determining that the payment is stuck is not an exact science, so the text should communicate that the SDK is “making a guess” rather than reporting a definite error. Further, the situation is expected to be rare; most users should never see this message. | It looks like the payment has not progressed for a while. Do you want to wait, or retry the payment in compatibility mode? | Det ser ut som betalingen din står fast. Vil du vente eller prøve på nytt i kompatibilitetsmodus? | Det verkar som din betalningen har fastnat. Vill du vänta kvar eller testa igen i kompatibilitetsläge? |
| maybeStuckAlertWait  | The button telling the SDK to wait when stuck. | Wait | Vent | Vänta | 
| maybeStuckAlertRetry | The button telling the SDK to retry when stuck. | Retry | Prøv på nytt | Testa igen | 
| swedbankpaysdk_retryable_error_title | Shown if there is a temporary error when starting a payment | Communication Error | Kommunikasjonsfeil | Kommunikationsfel |
| swedbankpaysdk_failed_init_consumer | Shown if the temporary error happened when starting the “checkin” phase | Could not initialize identification. | Vi kunne ikke starte identifiseringen. | Kunde inte påbörja identifieringen. | 
| swedbankpaysdk_failed_create_payment | Shown if the temporary error happened when starting the “checkout” phase | Could not initialize payment. | Vi kunne ikke starte betalingen. | Kunde inte påbörja betalningen. | 
| swedbankpaysdk_pull_to_refresh | Shown as part of the temporary error message | Pull down to retry. | Dra ned for å prøve igjen. | Dra nedåt för att försöka igen. | 
| swedbankpaysdk_js_prompt_hint | "Shown as the “hint” for the text field of a JavaScript prompt, if a 3D-Secure page displays one (this is unlikely to happen in practice, but it is possible). | Input | Fyll inn | Fyll i | 
| swedbankpaysdk_bad_init_request_title | Shown if there is a fatal error when starting a payment | Unable to start payment at this time | Kunne ikke starte betalingen nå | Det gick inte att starta betalningen | 
| swedbankpaysdk_terminal_failure_title | Shown if the payment menu makes an onError callback | Configuration Error | Noe er feil med oppsettet | Konfigurationsfel | 
| swedbankpaysdk_payment_complete | These are not expected to be seen by users normally. In addition, they are only used if the backend for the SDK is using the “Merchant Backend API” specification. These are all “error detail” messages, i.e. they are always shown in the context of either a “Communication Error” or a “Unable to Start Payment at This Time” message: | Payment complete | Betalingen er fullført | Betalat och klart | 
| swedbankpaysdk_payment_failed | Same as previous | Payment failed | Betalingen mislyktes | Vi kunde inte behandla din betalning | 
| swedbankpaysdk_problem_invalid_request | Shown if the backend returns an https://api.payex.com/psp/errordetail/mobilesdk/unauthorized problem (the Merchant Backend problems are documented in the developer portal: Mobile SDK – Merchant Backend ). This is generally a configuration error. | Service did not recognize the request. | Tjenesten kjente ikke igjen forespørselen. | Tjänsten kände inte igen förfrågan. | 
| swedbankpaysdk_problem_unauthorized | Shown if the backend returns an https://api.payex.com/psp/errordetail/mobilesdk/unauthorized problem (the Merchant Backend problems are documented in the developer portal: Mobile SDK – Merchant Backend ). This is generally a configuration error. | Service did not recognize the request. | Tjenesten kjente ikke igjen forespørselen. | Tjänsten kände inte igen förfrågan. | 
| swedbankpaysdk_problem_input_error | Shown if the backend returns an https://api.payex.com/psp/errordetail/inputerror problem when creating the payment (the common problems are documented in the developer portal: Introduction – Introduction ) | Invalid input data. | Informasjonen som er skrevet inn er ugyldig. | Den angivna informationen är ogiltig. | 
| swedbankpaysdk_problem_forbidden | Shown if the backend returns an https://api.payex.com/psp/errordetail/forbidden problem. | Forbidden | Ikke tillatt | Inte tillåtet | 
| swedbankpaysdk_problem_not_found | Shown if the backend returns an https://api.payex.com/psp/errordetail/notfound problem. | Not found | Ikke funnet | Hittades inte | 
| swedbankpaysdk_problem_backend_connection_timeout | Shown if the backend returns an https://api.payex.com/psp/errordetail/mobilesdk/gatewaytimeout problem. | Service did not respond in a timely fashion. Please try again. | Tjenesten brukte for lang tid på å svare. Vennligst prøv igjen. | Det tog för lång tid för tjänsten att svara.  | 
| swedbankpaysdk_problem_backend_connection_failure | Shown if the backend returns an https://api.payex.com/psp/errordetail/mobilesdk/badgateway problem and the problem was caused by a connection error. | Unable to connect to service. Please try again. | Fikk ikke kontakt med tjenesten. Vennligst prøv igjen. | Kunde inte ansluta till tjänsten. Försök igen. | 
| swedbankpaysdk_problem_invalid_backend_response | Shown if the backend returns an https://api.payex.com/psp/errordetail/mobilesdk/badgateway problem and the problem was caused by an unexpected response from the Swedbank Pay API. This is not an expected condition, but could signal a configuration or programming error. | Received invalid service response. Please try again. |  Tjenesten ga en ugyldig respons. Vennligst prøv igjen. | Felaktig respons från tjänsten. Försök igen. | 
| swedbankpaysdk_problem_system_error | Shown if the backend returns an https://api.payex.com/psp/errordetail/systemerror problem. | Internal service error. Please try again. | "Intern feil på tjenesten. Vennligst prøv igjen." | Internt fel på tjänsten. Försök igen. | 
| swedbankpaysdk_problem_configuration_error | Shown if the backend returns an https://api.payex.com/psp/errordetail/configurationerror problem. |  Invalid configuration. |  Oppsettet er ugyldig. | Felaktig konfiguration. | 
| swedbankpaysdk_problem_unknown | Shown if backend returns any other problem. | Unexpected error |  Det skjedde en uventet feil | Oväntat fel. | 
