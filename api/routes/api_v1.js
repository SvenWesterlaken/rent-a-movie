var express = require('express'),
    router = express.Router(),
    path = require('path'),
    pool = require('../database/db'),
    auth =  require('../auth/auth');

//hier worden alle uitgeleende films door customer getoond
router.get('/rentals/:userid', function (req, res) {
    var userId = req.params.id;
    res.contentType('application/json');
    pool.query('SELECT * FROM rental ' +
        'JOIN customer' +
        ' on rental.customer_id = customer.customer_id', [userId], function (errors, rows, fields) {
        if (errors){
            res.status(400).json(errors);
        }else {
            res.status(200).json(rows);
        };
    });

})

// Hier wordt de lijst van alle films getoond
router.get('/films', function (req, res) {

    res.contentType('application/json');
    pool.query('SELECT * FROM film LIMIT ? OFFSET ?',
        [req.query.limit, req.query.offset],
        function (error, rows, fields) {
        if (error) {
            res.status(400).json(error);
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
            res.status(400).json(errors);
        }else {
            res.status(200).json(rows);
        };
    });
});


module.exports = router;
