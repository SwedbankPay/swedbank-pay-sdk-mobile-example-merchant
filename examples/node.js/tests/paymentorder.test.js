const constants = require('../util/constants.js');
const app = require('../app.js');
const chai = require('chai');
const chaiHttp = require('chai-http');
const fs = require("fs");
const findOperation = require('../util/find-operation.js');
const testHelpers = require('../util/test-helpers.js');

const paymentorders = require('../routes/paymentorders.js');
const { celebrate, Joi, errors, Segments } = require('celebrate');

// Configure chai
chai.use(chaiHttp);
chai.should();

describe('Post PaymentOrder v3', () => {

	it('Payment order should be accepted', (done) => {

		const paymentOrder = JSON.parse(fs.readFileSync("tests/paymentOrderRequest_v3.json").toString());

		chai.request(app)
		.post('/paymentorders')
		.set(testHelpers.headers)
		.send(paymentOrder)
		.end((err, res) => {

			//testHelpers.printResult(res)
			testHelpers.checkCredentials(res);

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
		.set(testHelpers.headers)
		.send(paymentOrder)
		.end((err, res) => {

			//console.log(res.text)

			testHelpers.checkCredentials(res, false);
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
		.set(testHelpers.headers)
		.send(paymentOrder)
		.end((err, res) => {

			testHelpers.checkCredentials(res);
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
	  .set(testHelpers.headers)
	  .send(paymentOrder)
	  .end((err, res) => {

		//console.log(res.text + "\n(result from test)\n")
		testHelpers.checkCredentials(res);

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
	  .set(testHelpers.headers)
	  .send({ resource: "/psp/paymentorders/567ef38b-3704-444d-7082-08da05b8e84b", expand: ["paid", "failedAttempts", "failed", "history"] })
	  .end((err, res) => {

			testHelpers.printResult(res);
			testHelpers.checkCredentials(res);

			res.should.have.status(200);
			res.body.should.be.a('object');
			done();
	 });
	
  })
  .timeout(15 * 1000);  //usually it never takes more than one second
});
*/