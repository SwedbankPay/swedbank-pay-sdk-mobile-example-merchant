const constants = require('../util/constants.js');
const app = require('../app.js')
const chai = require('chai')
const chaiHttp = require('chai-http')

// Configure chai
chai.use(chaiHttp);
chai.should();

const env = process.env;
var headers = { Accept: 'application/json' }
headers[constants.apiKeyHeaderName] = env["API_KEY"]
headers[constants.accessTokenHeaderName] = env["MERCHANT_TOKEN"]

describe('Post PaymentOrder v2', () => {
  
  it('Payment order should be accepted', (done) => {
    chai.request(app)
      .post('/paymentorders')
      .set(headers)
      .send({
        "paymentorder": {
          "operation": "Purchase",
          "currency": "SEK",
          "amount": 1000,
          "vatAmount": 200,
          "description": "Order 12345678",
          "userAgent": "SwedbankPaySDK-Android/1.0",
          "language": "en-US",
          "generateRecurrenceToken": false,
          "urls": {
            "hostUrls": [
            "https://example.com/"
            ],
            "completeUrl": "https://example.com/complete",
            "cancelUrl": "https://example.com/cancel",
            "paymentUrl": "https://example.com/sdk-callback/android-intent?package=your.ecom.app&id=abcdef"
          },
          "payeeInfo": {
            "payeeId": "merchant-token",
            "payeeReference": "order-id"
          },
          "orderItems": [
          {
            "reference": "item987",
            "name": "Demo Item",
            "type": "PRODUCT",
            "class": "TestProduct",
            "quantity": 1,
            "quantityUnit": "pcs",
            "unitPrice": 1000,
            "vatPercent": 2500,
            "amount": 1000,
            "vatAmount": 200
          }
          ]
        }
      })
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('object');
        done();
     });
    
  })
  .timeout(15 * 1000);  //usually it never takes more than one second
})