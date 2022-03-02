const constants = require('../util/constants.js');
const app = require('../app.js');
const chai = require('chai');
const chaiHttp = require('chai-http');
const fs = require("fs");

const paymentorders = require('../routes/paymentorders.js');
const { celebrate, Joi, errors, Segments } = require('celebrate');

// Configure chai
chai.use(chaiHttp);
chai.should();

const env = process.env;
let headers = { 
  Accept: 'application/json', 
  [constants.apiKeyHeaderName]: [env["API_KEY"]],
  [constants.accessTokenHeaderName]: "doot_doot",
};

function checkCredentials(res) {
  chai.assert(res.status != 401, "Getting 401, is the credentials missing?\n" + res.text);
}

describe('Post PaymentOrder v3', () => {
  
  it('Payment order should be accepted', (done) => {

	const paymentOrder = JSON.parse(fs.readFileSync("tests/paymentOrderRequest_v3.json").toString());

	chai.request(app)
	  .post('/paymentorders')
	  .set(headers)
	  .send(paymentOrder)
	  .end((err, res) => {

		//console.log(res.text)
		checkCredentials(res);

		res.should.have.status(200);
		res.body.should.be.a('object');
		done();
	 });
	
  })
  .timeout(15 * 1000);  //usually it never takes more than one second
});

describe('Handle bad formatted PaymentOrder', () => {
  
  it('Payment order should NOT be accepted', (done) => {

	let paymentOrder = JSON.parse(fs.readFileSync("tests/paymentOrderRequest_v3.json").toString());
	paymentOrder.paymentorder.operation = "invalid setting";
	chai.request(app)
	  .post('/paymentorders')
	  .set(headers)
	  .send(paymentOrder)
	  .end((err, res) => {

		//console.log(res.text)

		checkCredentials(res);
		res.should.have.status(400);
		res.body.should.be.a('object');
		done();
	 });
  })
  .timeout(15 * 1000);  //usually it never takes more than one second
});

describe('Post PaymentOrder v2', () => {
  
  it('Payment order should be accepted', (done) => {

	const paymentOrder = JSON.parse(fs.readFileSync("tests/paymentOrderRequest_v2.json").toString());
	chai.request(app)
	  .post('/paymentorders')
	  .set(headers)
	  .send(paymentOrder)
	  .end((err, res) => {

		checkCredentials(res);
		res.should.have.status(200);
		res.body.should.be.a('object');
		done();
	 });
	
  })
  .timeout(15 * 1000);  //usually it never takes more than one second
});



/**
At this time, we do only support "PaymentsOnly" which means no checkin.

describe('Post PaymentOrder v3 with checkin', () => {
  
  it('Checkin order should be accepted', (done) => {

	const paymentOrder = JSON.parse(fs.readFileSync("tests/paymentOrderRequestCheckin_v3.json").toString());

	chai.request(app)
	  .post('/paymentorders')
	  .set(headers)
	  .send(paymentOrder)
	  .end((err, res) => {

		//console.log(res.text + "\n(result from test)\n")
		checkCredentials(res);

		res.should.have.status(200);
		res.body.should.be.a('object');
		done();
	 });
	
  })
  .timeout(15 * 1000);  //usually it never takes more than one second
});

describe('Expand payer in a v3 payment order', () => {
  
  it('Payer should be expanded', (done) => {

	chai.request(app)
	  .post("/expand") 
	  .set(headers)
	  .send({ resource: "/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6", expand: "payer" })
	  .end((err, res) => {

		//console.log(res.body.paymentOrder.payer)
		checkCredentials(res);

		res.should.have.status(200);
		res.body.should.be.a('object');
		done();
	 });
	
  })
  .timeout(15 * 1000);  //usually it never takes more than one second
});
*/

describe('Patch Instrument v3', () => {
  
  it('Payment order should be patched with a new Instrument', (done) => {

	const paymentOrder = JSON.parse(fs.readFileSync("tests/paymentOrderRequest_v3.json").toString());

	chai.request(app)
	  .post('/paymentorders')
	  .set(headers)
	  .send(paymentOrder)
	  .end((err, res) => {

		checkCredentials(res);

		res.should.have.status(200);
		res.body.should.be.a('object');

		var href = null
		for (const operation of res.body.operations) {
			if (operation.rel == "set-instrument") {
				
				href = operation.href
				break
			}
		}
		if (href == null) {
			console.log("error! No operation!")
		}

		//Now patch this payment order!
		let params = {
			href: href,
			paymentorder: {
				operation: "SetInstrument",
				instrument: "Swish"
			}
		}

		chai.request(app)
		  .post('/patch')
		  .set(headers)
		  .send(params)
		  .end((err, res) => {

		  	//console.log(res.text) 
		  	//console.log(err)
		  	res.should.have.status(200);

			done();
		})
	 });
	
  })
  .timeout(15 * 1000);  //usually it never takes more than one second
});