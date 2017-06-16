var express = require('express'),
    router = express.Router(),
    path = require('path'),
    bcrypt = require('bcrypt'),
    pool = require('../database/db'),
    auth =  require('../auth/auth');

router.all("/rentals/*", function (req, res, next) {
    var token = (req.header('W-Access-Token')) || '';
    auth.decodeToken(token, function (err, payload) {
        if (err) {
            console.log('Error handler: ' + err.message);
            res.status((err.status || 401 )).json({error: new Error("Not authorised").message});
        } else {
            next();
        }
    });
});

//Register a user with {"email" : "<email>", "password" : "<password>", "firstname" : "<firstname>", "lastname" : "<lastname>"}
router.post('/register', function(req, res) {
  const saltRounds = 10;
  var email = req.body.email || '',
      password = req.body.password || '',
      firstName = req.body.firstname || '',
      lastName = req.body.lastname || '';

  if (email != null && password != null) {
    query = 'SELECT * FROM customer WHERE email = "' + email + '";';

    pool.getConnection( function(error, connection) {
      if (error) { throw error }
      connection.query(query, function (error, rows, fields) {
        connection.release();
        if(error) {
          throw error
        }

        // Generate JWT
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
    res.status(404).json({"msg" : "No register credentials in the body."});
  }


});

router.post('/login', function(req, res) {
  var email = req.body.email || '';
  var password = req.body.password || '';

  if (email != null && password != null) {
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
            console.log(response);
            if(bcrypt.compareSync(password, response['password'])) {
              res.status(200).json({"token" : auth.encodeToken(email), "email" : email});
            }
        } else {
            res.status(401).json({"error":"Invalid credentials"})
        }
      })
    });

  } else {
    res.status(404).json({"msg" : "No login credentials in the body."});
  }

});


//hier worden alle uitgeleende films door customer getoond
router.get('/rentals/:userid', function (req, res) {
    var userId = req.params.id;
    res.contentType('application/json');
    pool.query('SELECT * FROM rental ' +
        'JOIN customer' +
        ' on rental.customer_id = customer.customer_id', [userId], function (errors, rows, fields) {
        if (errors){
            throw errors
        }else {
            res.status(200).json(rows);
        };
    });

});

router.post('rentals/:userid/:inventoryid', function (req, res) {
    var rental_id = req.body.rental_id || '',
        rental_date = req.body.rental_date || '',
        inventory_id = req.body.inventory_id ||'',
        custormer_id = req.body.customer_id || '',
        return_date = req.body.return_date || '',
        staff_id = req.body.staff_id || ''
    // var inventoryId = get('inventory_id');
    // var customerId = get('customer_id');
    // var returnDate = get('return_date');
    // var staffId = get('staff_id');
    // var lastUpdate = get('last_update')

    pool.query(
        'INSERT INTO rental (rental_id, rental_date, inventory_id, customer_id, return_date, staff_id, last_update)' +
        'VALUES ("' + rental_id + '", "' + return_date + '", "' + inventory_id + '", "' + custormer_id + '", "' + return_date + '", "' + staff_id + '",)',
        function (error,rows,fields) {
            if (error)
                throw error;
            else {
                res.status(200);
                res.contentType('application/json');
                res.json(rows);
            }
        });
});

// Hier wordt de lijst van alle films getoond
router.get('/films', function (req, res) {

    var offset = parseInt(req.query.offset),
        count = parseInt(req.query.count);


    res.contentType('application/json');
    pool.query('SELECT * FROM film LIMIT ? OFFSET ?',
        [count, offset],
        function (error, rows, fields) {
        if (error) {
            throw error
        } else {
            res.status(200).json(rows);
        };
    });
});

// hier word een film getoont op id naar keuze
router.get('/films/:id', function (req,res) {
    var filmId = req.params.id;
    res.contentType('application/json');
    pool.query('SELECT * FROM film WHERE film_id=?', [filmId], function (errors, rows, fields) {
        if (errors){
            throw errors
        }else {
            res.status(200).json(rows);
        };
    });
});

router.get('*', function(request, response) {
  response.status(404);
  response.json({
    "msg": "Api endpoint not available"
  });
});


module.exports = router;
