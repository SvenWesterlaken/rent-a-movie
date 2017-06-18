var chai = require('chai'),
    chai_http = require('chai-http'),
    server = require('../index.js'),
    auth = require('../auth/auth'),
    test = require('./functions'),
    chai_arrays = require('chai-arrays'),
    chai_things = require('chai-things'),
    expect = chai.expect;

chai.use(chai_http);
chai.use(chai_arrays);
chai.use(chai_things);

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

  it('Unsupported API URL (Error message)', function(done) {
    chai.request(server)
      .get('/api/v1/info')
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(404);
        expect(res).to.be.json;
        expect(res.body).to.include({"msg": "Api endpoint not available"});
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

describe('User registration', function(done) {
  var randomNumber = Math.floor((Math.random() * 1000000000) + 1),
      userEmail = "testUser" + randomNumber + "@gmail.com",
      password = Math.random().toString(36).slice(-8),
      firstName = "Subject",
      lastName = randomNumber;


  it('Valid registration', function(done) {
    chai.request(server)
      .post('/api/v1/register')
      .send({"email" : userEmail, "password" : password, "firstname" : firstName, "lastname" : lastName})
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(JSON.parse(res.text)).to.have.property("affectedRows", 1);
        done();
      });
  });

  it('User already exists', function(done) {
    chai.request(server)
      .post('/api/v1/register')
      .send({"email" : userEmail, "password" : password, "firstname" : firstName, "lastname" : lastName})
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "User already exists."});
        done();
      });
  });

  it('No information given', function(done) {
    chai.request(server)
      .post('/api/v1/register')
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No(t enough) register credentials in the body."});
        done();
      });
  });
});

describe('Get films', function() {
  it('Get all films', function(done) {
    chai.request(server)
      .get('/api/v1/films')
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(res.body).to.be.array();
        expect(res.body).to.be.ofSize(1000);
        done();
      });
  });

  it('Get films 11 to 30', function(done) {
    chai.request(server)
      .get('/api/v1/films?offset=10&count=20')
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(res.body).to.be.array();
        expect(res.body).to.be.ofSize(20);
        expect(res.body).to.not.contain.an.item.with.property('film_id', 9);
        expect(res.body).to.contain.an.item.with.property('film_id', 11);
        expect(res.body).to.contain.an.item.with.property('film_id', 30);
        expect(res.body).to.not.contain.an.item.with.property('film_id', 31);
        done();
      });
  });
});

describe('Get a film', function() {
  it('Get film 9', function(done) {
    chai.request(server)
      .get('/api/v1/films/9')
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(res.body).to.be.ofSize(1);
        expect(res.body).to.contain.an.item.with.property('film_id', 9);
        done();
      });
  });

  it('Get film 31', function(done) {
    chai.request(server)
      .get('/api/v1/films/31')
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(res.body).to.be.ofSize(1);
        expect(res.body).to.contain.an.item.with.property('film_id', 31);
        done();
      });
  });
});

var userEmail = "sgaweste@avans.nl";

describe('Get rentals of a user', function() {

  it('Get rentals from user 12', function(done) {
    chai.request(server)
      .get('/api/v1/rentals/12')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(res.body).to.be.ofSize(1);
        expect(res.body).to.contain.an.item.with.property('inventory_id', 988);
        done();
      });
  });

  it('No acces token given', function(done) {
    chai.request(server)
      .get('/api/v1/rentals/12')
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error": "Not authorised"});
        done();
      });
  });

  it('String given as user ID', function(done) {
    chai.request(server)
      .get('/api/v1/rentals/test')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper user ID given"});
        done();
      });
  });

  it('User ID 0', function(done) {
    chai.request(server)
      .get('/api/v1/rentals/0')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper user ID given"});
        done();
      });
  });

  it('No rentals found (customer 600)', function(done) {
    chai.request(server)
      .get('/api/v1/rentals/600')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(404);
        expect(res.body).to.include({"msg" : "No rentals found"});
        done();
      });
  });
});

describe('Add a rental', function() {
  it('Succesful insert', function(done) {
    chai.request(server)
      .post('/api/v1/rentals/14/50')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(JSON.parse(res.text)).to.have.property("affectedRows", 1);
        done();
      });
  });

  it('String given as user ID', function(done) {
    chai.request(server)
      .post('/api/v1/rentals/test/50')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper user ID given"});
        done();
      });
  });

  it('User ID 0', function(done) {
    chai.request(server)
      .post('/api/v1/rentals/0/50')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper user ID given"});
        done();
      });
  });

  it('String given as inventory ID', function(done) {
    chai.request(server)
      .post('/api/v1/rentals/14/test')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper inventory ID given"});
        done();
      });
  });

  it('Inventory ID 0', function(done) {
    chai.request(server)
      .post('/api/v1/rentals/14/0')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper inventory ID given"});
        done();
      });
  });
});

describe('Extend a rental (change)', function() {
  it('Succesful extend', function(done) {
    chai.request(server)
      .put('/api/v1/rentals/14/50')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(JSON.parse(res.text).affectedRows).to.not.equal(0);
        expect(JSON.parse(res.text).changedRows).to.not.equal(0);
        done();
      });
  });

  it('No acces token given', function(done) {
    chai.request(server)
      .put('/api/v1/rentals/14/50')
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error": "Not authorised"});
        done();
      });
  });

  it('String given as user ID', function(done) {
    chai.request(server)
      .put('/api/v1/rentals/test/50')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper user ID given"});
        done();
      });
  });

  it('User ID 0', function(done) {
    chai.request(server)
      .put('/api/v1/rentals/0/50')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper user ID given"});
        done();
      });
  });

  it('String given as inventory ID', function(done) {
    chai.request(server)
      .put('/api/v1/rentals/14/test')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper inventory ID given"});
        done();
      });
  });

  it('Inventory ID 0', function(done) {
    chai.request(server)
      .put('/api/v1/rentals/14/0')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper inventory ID given"});
        done();
      });
  });
});

describe('Remove a rental', function() {
  it('Succesful removal', function(done) {
    chai.request(server)
      .delete('/api/v1/rentals/14/50')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(JSON.parse(res.text).affectedRows).to.not.equal(0);
        expect(JSON.parse(res.text).changedRows).to.not.equal(0);
        done();
      });
  });

  it('No acces token given', function(done) {
    chai.request(server)
      .delete('/api/v1/rentals/14/50')
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error": "Not authorised"});
        done();
      });
  });

  it('String given as user ID', function(done) {
    chai.request(server)
      .delete('/api/v1/rentals/test/50')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper user ID given"});
        done();
      });
  });

  it('User ID 0', function(done) {
    chai.request(server)
      .delete('/api/v1/rentals/0/50')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper user ID given"});
        done();
      });
  });

  it('String given as inventory ID', function(done) {
    chai.request(server)
      .delete('/api/v1/rentals/14/test')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper inventory ID given"});
        done();
      });
  });

  it('Inventory ID 0', function(done) {
    chai.request(server)
      .delete('/api/v1/rentals/14/0')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.not.be.null;
        expect(res).to.have.status(401);
        expect(res.body).to.include({"error" : "No proper inventory ID given"});
        done();
      });
  });
});
