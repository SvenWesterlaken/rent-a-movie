var chai = require('chai'),
    chai_http = require('chai-http'),
    server = require('../index.js'),
    auth = require('../auth/auth'),
    test = require('./functions'),
    chai_arrays = require('chai-arrays'),
    expect = chai.expect;

chai.use(chai_http);
chai.use(chai_arrays)

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

  it('No information', function(done) {
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


var film11 = {
  "film_id": 11,
  "title": "ALAMO VIDEOTAPE",
  "description": "A Boring Epistle of a Butler And a Cat who must Fight a Pastry Chef in A MySQL Convention",
  "release_year": 2006,
  "language_id": 1,
  "original_language_id": null,
  "rental_duration": 6,
  "rental_rate": 0.99,
  "length": 126,
  "replacement_cost": 16.99,
  "rating": "G",
  "special_features": "Commentaries,Behind the Scenes",
  "last_update": "2006-02-15T04:03:42.000Z"
};

var film30 = {
  "film_id": 30,
  "title": "ANYTHING SAVANNAH",
  "description": "A Epic Story of a Pastry Chef And a Woman who must Chase a Feminist in An Abandoned Fun House",
  "release_year": 2006,
  "language_id": 1,
  "original_language_id": null,
  "rental_duration": 4,
  "rental_rate": 2.99,
  "length": 82,
  "replacement_cost": 27.99,
  "rating": "R",
  "special_features": "Trailers,Deleted Scenes,Behind the Scenes",
  "last_update": "2006-02-15T04:03:42.000Z"
};

var film9 = {
  "film_id": 9,
  "title": "ALABAMA DEVIL",
  "description": "A Thoughtful Panorama of a Database Administrator And a Mad Scientist who must Outgun a Mad Scientist in A Jet Boat",
  "release_year": 2006,
  "language_id": 1,
  "original_language_id": null,
  "rental_duration": 3,
  "rental_rate": 2.99,
  "length": 114,
  "replacement_cost": 21.99,
  "rating": "PG-13",
  "special_features": "Trailers,Deleted Scenes",
  "last_update": "2006-02-15T04:03:42.000Z"
};

var film31 = {
  "film_id": 31,
  "title": "APACHE DIVINE",
  "description": "A Awe-Inspiring Reflection of a Pastry Chef And a Teacher who must Overcome a Sumo Wrestler in A U-Boat",
  "release_year": 2006,
  "language_id": 1,
  "original_language_id": null,
  "rental_duration": 5,
  "rental_rate": 4.99,
  "length": 92,
  "replacement_cost": 16.99,
  "rating": "NC-17",
  "special_features": "Commentaries,Deleted Scenes,Behind the Scenes",
  "last_update": "2006-02-15T04:03:42.000Z"
};

describe('Get films', function() {
  it('Get all films', function(done) {
    chai.request(server)
      .get('/api/v1/films')
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(res.body).to.be.array();
        expect(res.body).to.include.deep.members([film9, film11, film30, film31]);
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
        expect(res.body).to.include.deep.members([film11, film30]).but.not.include.deep.members([film9, film31]);
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
        expect(res.body).to.include.deep.members([film9]);
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
        expect(res.body).to.include.deep.members([film31]);
        done();
      });
  });
});

var rental988 = {
  "rental_id": 988,
  "rental_date": "2005-05-30T21:08:03.000Z",
  "inventory_id": 1364,
  "customer_id": 12,
  "return_date": "2005-06-06T22:22:03.000Z",
  "staff_id": 1,
  "last_update": "2006-02-15T19:30:53.000Z"
};

var userEmail = "sgaweste@avans.nl";

describe('Get rentals of a user', function() {

  it('Get rentals from user 12', function(done) {
    chai.request(server)
      .get('/api/v1/rentals/12')
      .set('W-Access-Token', auth.encodeToken(userEmail))
      .end(function(err, res) {
        expect(err).to.be.null;
        expect(res).to.have.status(200);
        expect(res.body).to.be.ofSize(28);
        //expect(res.body).to.have.nested.property("rental_id", 988);
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

// describe('Add a rental', function() {
//   it('Succesful insert', function(done) {
//     chai.request(server)
//       .post('/api/v1/rentals/2/50')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.be.null;
//         expect(res).to.have.status(200);
//         expect(JSON.parse(res.text)).to.have.property("affectedRows", 1);
//         done();
//       });
//   });
//
//   it('No acces token given', function(done) {
//     chai.request(server)
//       .post('api/v1/rentals/2/50')
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error": "Not authorised"});
//         done();
//       });
//   });
//
//   it('String given as user ID', function(done) {
//     chai.request(server)
//       .post('api/v1/rentals/test/50')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper user ID given"});
//         done();
//       });
//   });
//
//   it('User ID 0', function(done) {
//     chai.request(server)
//       .post('api/v1/rentals/0/50')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper user ID given"});
//         done();
//       });
//   });
//
//   it('String given as inventory ID', function(done) {
//     chai.request(server)
//       .post('api/v1/rentals/2/test')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper inventory ID given"});
//         done();
//       });
//   });
//
//   it('Inventory ID 0', function(done) {
//     chai.request(server)
//       .post('api/v1/rentals/2/0')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper inventory ID given"});
//         done();
//       });
//   });
// });
//
// describe('Extend a rental (change)', function() {
//   it('Succesful extend', function(done) {
//     chai.request(server)
//       .put('/api/v1/rentals/2/50')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.be.null;
//         expect(res).to.have.status(200);
//         expect(JSON.parse(res.text)).to.have.property("affectedRows", 1);
//         expect(JSON.parse(res.text)).to.have.property("changedRows", 1);
//         done();
//       });
//   });
//
//   it('No acces token given', function(done) {
//     chai.request(server)
//       .put('api/v1/rentals/2/50')
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error": "Not authorised"});
//         done();
//       });
//   });
//
//   it('String given as user ID', function(done) {
//     chai.request(server)
//       .put('api/v1/rentals/test/50')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper user ID given"});
//         done();
//       });
//   });
//
//   it('User ID 0', function(done) {
//     chai.request(server)
//       .put('api/v1/rentals/0/50')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper user ID given"});
//         done();
//       });
//   });
//
//   it('String given as inventory ID', function(done) {
//     chai.request(server)
//       .put('api/v1/rentals/2/test')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper inventory ID given"});
//         done();
//       });
//   });
//
//   it('Inventory ID 0', function(done) {
//     chai.request(server)
//       .put('api/v1/rentals/2/0')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper inventory ID given"});
//         done();
//       });
//   });
// });
//
// describe('Remove a rental', function() {
//   it('Succesful removal', function(done) {
//     chai.request(server)
//       .put('/api/v1/rentals/2/50')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.be.null;
//         expect(res).to.have.status(200);
//         expect(JSON.parse(res.text)).to.have.property("affectedRows", 1);
//         done();
//       });
//   });
//
//   it('Rental is already removed', function(done) {
//     chai.request(server)
//       .put('/api/v1/rentals/2/50')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.be.null;
//         expect(res).to.have.status(200);
//         expect(JSON.parse(res.text)).to.have.property("affectedRows", 0);
//         done();
//       });
//   });
//
//   it('No acces token given', function(done) {
//     chai.request(server)
//       .put('api/v1/rentals/2/50')
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error": "Not authorised"});
//         done();
//       });
//   });
//
//   it('String given as user ID', function(done) {
//     chai.request(server)
//       .put('api/v1/rentals/test/50')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper user ID given"});
//         done();
//       });
//   });
//
//   it('User ID 0', function(done) {
//     chai.request(server)
//       .put('api/v1/rentals/0/50')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper user ID given"});
//         done();
//       });
//   });
//
//   it('String given as inventory ID', function(done) {
//     chai.request(server)
//       .put('api/v1/rentals/2/test')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper inventory ID given"});
//         done();
//       });
//   });
//
//   it('Inventory ID 0', function(done) {
//     chai.request(server)
//       .put('api/v1/rentals/2/0')
//       .set('W-Access-Token', auth.encodeToken(userEmail))
//       .end(function(err, res) {
//         expect(err).to.not.be.null;
//         expect(res).to.have.status(401);
//         expect(res.body).to.include({"error" : "No proper inventory ID given"});
//         done();
//       });
//   });
// });
