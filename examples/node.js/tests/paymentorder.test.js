const constants = require('../util/constants.js');
const app = require('../app.js');
const chai = require('chai');
const chaiHttp = require('chai-http');
const fs = require("fs");

// Configure chai
chai.use(chaiHttp);
chai.should();

const env = process.env;
let headers = { 
  Accept: 'application/json', 
  [constants.apiKeyHeaderName]: [env["API_KEY"]],
  [constants.accessTokenHeaderName]: [env["MERCHANT_TOKEN"]],
};

describe('Post PaymentOrder v2', () => {
  
  it('Payment order should be accepted', (done) => {

    const paymentOrderV2 = JSON.parse(fs.readFileSync("tests/paymentOrderRequest_v2.json").toString());
    chai.request(app)
      .post('/paymentorders')
      .set(headers)
      .send(paymentOrderV2)
      .end((err, res) => {
        res.should.have.status(200);
        res.body.should.be.a('object');
        done();
     });
    
  })
  .timeout(15 * 1000);  //usually it never takes more than one second
});