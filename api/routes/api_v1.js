var express = require('express'),
    router = express.Router(),
    path = require('path'),
    bcrypt = require('bcryptjs'),
    pool = require('../database/db'),
    auth =  require('../auth/auth');

//Check for all rental endpoints if the user has provided an acces-token
router.all("/rentals/*", function (req, res, next) {
    var token = (req.header('W-Access-Token')) || '';
    auth.decodeToken(token, function (err, payload) {
        if (err) {
            //console.log('Error handler: ' + err.message);
            res.status((err.status || 401 )).json({error: new Error("Not authorised").message});
        } else {
            next();
        }
    });
});

//Register a user with {"email" : "<email>", "password" : "<password>", "firstname" : "<firstname>", "lastname" : "<lastname>"} in the body
router.post('/register', function(req, res) {
  const saltRounds = 10;
  var email = req.body.email || '',
      password = req.body.password || '',
      firstName = req.body.firstname || '',
      lastName = req.body.lastname || '',
      id = req.body.id || '';

  if (email != '' && password != '' && firstName != '' && lastName != '') {
    query = 'SELECT * FROM customer WHERE email = "' + email + '";';

    pool.getConnection( function(error, connection) {
      if (error) { throw error }
      connection.query(query, function (error, rows, fields) {
        connection.release();
        if(error) {
          throw error
        }

        if( rows[0] ) {
            res.status(401).json({"error" : "User already exists."});
        } else {

          password = bcrypt.hashSync(password, saltRounds);

          query_add = 'INSERT INTO `customer` (email, password, first_name, last_name, create_date) VALUES ("' + email + '", "' + password + '", "' + firstName + '", "' + lastName + '", CURRENT_TIMESTAMP);'

            pool.getConnection( function(error, connection) {
              if (error) { throw error }
              connection.query(query_add, function(error, rows, fields) {
                connection.release();

                if(error) {
                  res.status(401).json({"error":"Customer has not been added"})
                  throw error
                }

                res.status(200).end(JSON.stringify(rows));

              });
            });
        }
      });
    });

  } else {
    res.status(401).json({"error" : "No(t enough) register credentials in the body."});
  }


});

//Login with {"email" : "<email>", "password" : "<password>"} in the body
router.post('/login', function(req, res) {
  var email = req.body.email || '';
  var password = req.body.password || '';

  if (email != '' && password != '') {
    query = 'SELECT * FROM `customer` WHERE email = "' + email + '";';

    pool.getConnection( function(error, connection) {
      if (error) { throw error }
      connection.query(query, function (error, rows, fields) {
        connection.release();
        if(error) {
          throw error
        }

        if( rows[0] ) {
            var response = JSON.parse(JSON.stringify(rows[0]));
            //console.log(response);
            if(bcrypt.compareSync(password, response['password'])) {
              res.status(200).json({"token" : auth.encodeToken(email), "email" : email});
            } else {
              res.status(401).json({"error":"Invalid password"});
            }
        } else {
            res.status(401).json({"error":"User not found"});
        }
      })
    });

  } else {
    res.status(401).json({"error" : "No(t enough) login credentials in the body."});
  }

});

router.get('/inventory/:filmid', function(req, res) {
  var filmID = req.params.filmid || 0;

  if(isNaN(filmID) || filmID == 0) {
    res.status(401).json({"error" : "No proper film ID given"});
  } else {
    var id = parseInt(filmID);

    res.contentType('application/json');

    pool.getConnection( function(error, connection) {
      if (error) { throw error }
      pool.query('SELECT * FROM `view_rental` WHERE film_id=?', [id], function (errors, rows, fields) {
        connection.release();
          if (errors){
              throw errors
          } else {
            if(rows[0]) {
              res.status(200).json(rows);
            } else {
              res.status(404).json({"msg" : "No items found"});
            }

          };
      });
    });
  }
});

//Get rentals from a user
router.get('/rentals/:userid', function (req, res) {
    var userID = req.params.userid || 0;

    if(isNaN(userID) || userID == 0) {
      res.status(401).json({"error" : "No proper user ID given"});
    } else {
      var id = parseInt(userID);

      res.contentType('application/json');

      pool.getConnection( function(error, connection) {
        if (error) { throw error }
        pool.query('SELECT * FROM view_rental WHERE customer_id=?', [id], function (errors, rows, fields) {
          connection.release();
            if (errors){
                throw errors
            } else {
              if(rows[0]) {
                res.status(200).json(rows);
              } else {
                res.status(404).json({"msg" : "No rentals found"});
              }

            };
        });
      });
    }


});

//Add a new rental
router.post('/rentals/:userid/:inventoryid', function (req, res) {
  var inventoryID = req.params.inventoryid || 0,
      userID = req.params.userid || 0,
      staffID = Math.floor((Math.random() * 5) + 1);

  if(isNaN(userID) || userID == 0) {
    res.status(401).json({"error" : "No proper user ID given"});
  } else if (isNaN(inventoryID) || inventoryID == 0) {
    res.status(401).json({"error" : "No proper inventory ID given"});
  } else {
    var query = 'INSERT INTO rental (inventory_id, customer_id, rental_date, return_date, staff_id, available) VALUES ' +
                '("' + inventoryID + '", "' + userID + '", CURRENT_TIMESTAMP, DATE_ADD(NOW(), INTERVAL 3 WEEK), "' + staffID + '", 0)';


    pool.getConnection( function(error, connection) {
      if (error) { throw error }
      connection.query(query, function (error, rows, fields) {
        connection.release();
    	  if (error) {
          throw error;
        }
    	  res.status(200).json(rows);
    	});
    });
  }
});

//Extend rental (change)
router.put('/rentals/:userid/:inventoryid', function (req, res) {
  var inventoryID = req.params.inventoryid || 0,
      userID = req.params.userid || 0;

  if(isNaN(userID) || userID == 0) {
    res.status(401).json({"error" : "No proper user ID given"});
  } else if (isNaN(inventoryID) || inventoryID == 0) {
    res.status(401).json({"error" : "No proper inventory ID given"});
  } else {
    pool.getConnection( function(error, connection) {
      if (error) { throw error }
      connection.query('SELECT return_date FROM rental WHERE customer_id=? AND inventory_id=?', [parseInt(userID), parseInt(inventoryID)], function (error, rows, fields) {
        connection.release();
    	  if (error) {
          throw error;
        }

        if(rows[0]) {
          var returnDate = JSON.parse(JSON.stringify(rows[0])).return_date;

          pool.getConnection( function(error, connection) {
            if (error) { throw error }
            connection.query('UPDATE rental SET return_date=DATE_ADD("' + returnDate + '", INTERVAL 3 WEEK) WHERE customer_id=? AND inventory_id=?', [parseInt(userID), parseInt(inventoryID)], function (error, rows, fields) {
              connection.release();
          	  if (error) {
                throw error;
              }
          	  res.status(200).json(rows);
          	});
          });

        } else {

        }
    	});
    });
  }
});

//Remove a rental
router.delete('/rentals/:userid/:inventoryid', function (req, res) {
  var inventoryID = req.params.inventoryid || 0,
      userID = req.params.userid || 0;

  if(isNaN(userID) || userID == 0) {
    res.status(401).json({"error" : "No proper user ID given"});
  } else if (isNaN(inventoryID) || inventoryID == 0) {
    res.status(401).json({"error" : "No proper inventory ID given"});
  } else {
    pool.getConnection( function(error, connection) {
      if (error) { throw error }
      connection.query('UPDATE rental SET available=1, return_date=CURRENT_TIMESTAMP WHERE customer_id=? AND inventory_id=?', [parseInt(userID), parseInt(inventoryID)], function (error, rows, fields) {
        connection.release();
    	  if (error) {
          throw error;
        }
    	  res.status(200).json(rows);
    	});
    });
  }
});

// Get (a) movie(s)
router.get('/films/:id?', function (req, res) {

    var offset = req.query.offset || '',
        count = req.query.count || '',
        filmID = req.params.id || ''

    res.contentType('application/json');

    var query = 'SELECT * FROM film ';

    if(filmID != '') {
      query = query + 'WHERE film_id="' + filmID + '";';
    }

    if(offset != '' && count != '') {
      query = query + 'LIMIT ' + parseInt(count) + ' OFFSET ' + parseInt(offset) + ';';
    }

    pool.getConnection( function(error, connection) {
      if (error) { throw error }
      connection.query(query, function (error, rows, fields) {
        connection.release();
    	  if (error) {
          throw error;
        }
    	  res.status(200).json(rows);
    	});
    });


});

router.get('*', function(request, response) {
  response.status(404);
  response.json({"msg": "Api endpoint not available"});
});


module.exports = router;
