var express = require('express'),
    router = express.Router(),
    path = require('path'),
    pool = require('../database/db'),
    auth =  require('../auth/auth');

module.exports = router;
