var chai = require('chai'),
    chai_http = require('chai-http'),
    server = require('../index.js'),
    auth = require('../auth/auth'),
    test = require('./functions'),
    should = chai.should(),
    expect = chai.expect;

chai.use(chai_http);

console.log(test.getUsersCount());

describe('Standard urls', function() {
  it('Hello message', function(done) {
    chai.request(server)
      .get('/hello')
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(res).to.be.json;
        expect(res.body).to.include({"msg" : "Hi, have a nice NodeJS day."});
        done();
      });
  });
  it('Unsupported URL (Error message)', function(done) {
    chai.request(server)
      .get('/admin')
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(404);
        expect(res).to.be.json;
        expect(res.body).to.include({"error" : "Request endpoint not found"});
        done();
      });
  });
});

describe('User login', function() {
  var userEmail = "sgaweste@avans.nl";

  it('Valid login', function(done) {
    chai.request(server)
      .post('/api/v1/login')
      .send({email: userEmail, password: 'test1234'})
      .end(function (err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(res.body).to.include({"token" : auth.encodeToken(userEmail)});
        expect(res.body).to.include({"email" : userEmail});
        done();
      });
  });

  it('Invalid password', function(done) {
    chai.request(server)
      .post('/api/v1/login')
      .send({email: userEmail, password: 'test'})
      .end(function (err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "Invalid password"});
        done();
      });
  });

  it('Invalid email', function(done) {
    chai.request(server)
      .post('/api/v1/login')
      .send({email: "invalid@hotmail.com", password: 'test1234'})
      .end(function (err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "User not found"});
        done();
      });
  });

  it('No email and password', function(done) {
    chai.request(server)
      .post('/api/v1/login')
      .end(function (err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No(t enough) login credentials in the body."});
        done();
      });

  });
});

// describe('User registration', function(done) {
//   it('Valid registration', function(done) {
//     chai.request(server)
//       .post('/api/v1/register')
//   });
// });
//
//
describe('Get films', function() {
  it('Get all films', function(done) {
    chai.request(server)
      .get('/api/v1/films')
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(res.body).to.be.a('array');
        done();
      });
  });
});
