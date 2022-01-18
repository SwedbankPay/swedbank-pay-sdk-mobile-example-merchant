Unified strings files for all platforms.

| Key | Description | English | Norwegian (bokmål) | Swedish |
| --- | ----------- | ------- | ------------------ | ------- |
| OK | General ok message | OK | OK | OK |
| Cancel | General cancellation | Cancel | Avbryt | Avbryt |
| browserAlertTitle | Description missing | You're almost done! | Du er nesten ferdig! | Du är snart klar! |
| browserAlertBody | Description missing | Your payment is being processed. Return to your browser to continue. | Betalingen din behandles. Gå tilbake til nettleseren for å fortsette. | Din betalning bearbetas. Gå tillbaka till webbläsaren för att fortsätta. |
| maybeStuckAlertTitle | Description missing | Stuck?
| maybeStuckAlertBody | Description missing | It looks like the payment has not progressed for a while. Do you want to wait, or retry the payment in compatibility mode? |      |      | 
| maybeStuckAlertWait  | Description missing | Wait |      |      | 
| maybeStuckAlertRetry | Description missing | Retry |      |      | 
| keyMissing | Shown if there is a temporary error when starting a payment | Communication Error | Kommunikasjonsfeil | Kommunikationsfel |
| keyMissing | Shown if the temporary error happened when starting the “checkin” phase | Could not initialize identification. | Vi kunne ikke starte identifiseringen. | Kunde inte påbörja identifieringen. | 
| keyMissing | Shown if the temporary error happened when starting the “checkout” phase | Could not initialize payment. | Vi kunne ikke starte betalingen. | Kunde inte påbörja betalningen. | 
| keyMissing | Shown as part of the temporary error message | Pull down to retry. | Dra ned for å prøve igjen. |  | 
| keyMissing | "Shown as the “hint” for the text field of a JavaScript prompt, if a 3D-Secure page displays one (this is unlikely to happen in practice, but it is possible). | Input | Fyll inn | Fyll i | 
| keyMissing | Shown if there is a fatal error when starting a payment | Unable to start payment at this time | Kunne ikke starte betalingen nå | Det gick inte att starta betalningen | 
| keyMissing | Shown if the payment menu makes an onError callback | Configuration Error | Noe er feil med oppsettet | Konfigurationsfel | 
| keyMissing | These are not expected to be seen by users normally. In addition, they are only used if the backend for the SDK is using the “Merchant Backend API” specification. These are all “error detail” messages, i.e. they are always shown in the context of either a “Communication Error” or a “Unable to Start Payment at This Time” message: | Payment complete | Betalingen er fullført | Betalat och klart | 
| keyMissing | Same as previous | Payment failed | Betalingen mislyktes | Vi kunde inte behandla din betalning | 
| keyMissing | Shown if the backend returns an https://api.payex.com/psp/errordetail/mobilesdk/unauthorized problem (the Merchant Backend problems are documented in the developer portal: Mobile SDK – Merchant Backend ). This is generally a configuration error. | Service did not recognize the request. | Tjenesten kjente ikke igjen forespørselen. | Tjänsten kände inte igen förfrågan. | 
| keyMissing | Shown if the backend returns an https://api.payex.com/psp/errordetail/inputerror problem when creating the payment (the common problems are documented in the developer portal: Introduction – Introduction ) | Invalid input data. | Informasjonen som er skrevet inn er ugyldig. | Den angivna informationen är ogiltig. | 
| keyMissing | Shown if the backend returns an https://api.payex.com/psp/errordetail/forbidden problem. | Forbidden | Ikke tillatt | Inte tillåtet | 
| keyMissing | Shown if the backend returns an https://api.payex.com/psp/errordetail/notfound problem. | Not found | Ikke funnet | Hittades inte | 
| keyMissing | Shown if the backend returns an https://api.payex.com/psp/errordetail/mobilesdk/gatewaytimeout problem. | Service did not respond in a timely fashion. Please try again. | Tjenesten brukte for lang tid på å svare. Vennligst prøv igjen. | Det tog för lång tid för tjänsten att svara.  | 
| keyMissing | Shown if the backend returns an https://api.payex.com/psp/errordetail/mobilesdk/badgateway problem and the problem was caused by a connection error. | Unable to connect to service. Please try again. | Fikk ikke kontakt med tjenesten. Vennligst prøv igjen. | Kunde inte ansluta till tjänsten. Försök igen. | 
| keyMissing | Shown if the backend returns an https://api.payex.com/psp/errordetail/mobilesdk/badgateway problem and the problem was caused by an unexpected response from the Swedbank Pay API. This is not an expected condition, but could signal a configuration or programming error. | Received invalid service response. Please try again. |  Tjenesten ga en ugyldig respons. Vennligst prøv igjen. | Felaktig respons från tjänsten. Försök igen. | 
| keyMissing | Shown if the backend returns an https://api.payex.com/psp/errordetail/systemerror problem. | Internal service error. Please try again. | "Intern feil på tjenesten. Vennligst prøv igjen." | Internt fel på tjänsten. Försök igen. | 
| keyMissing | Shown if the backend returns an https://api.payex.com/psp/errordetail/configurationerror problem. |  Invalid configuration. |  Oppsettet er ugyldig. | Felaktig konfiguration. | 
| keyMissing | Shown if backend returns any other problem. | Unexpected error |  Det skjedde en uventet feil | Oväntat fel. | 
