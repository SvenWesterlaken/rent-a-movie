var http = require('http'),
    express = require('express'),
    app = express(),
    config = require('./config.json'),
    bodyParser = require('body-parser'),
    port = process.env.PORT || config.webPort;

app.set('SECRET_KEY', config.secretkey);

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.all('*', function(req, res, next){
  //console.log(req.method + " " + req.url);
  next();
});

app.get('/hello', function(req, res, next){
  res.contentType('application/json');
  res.status(200);
  res.json({"msg": "Hi, have a nice NodeJS day."})
});

app.use('/api/v1', require('./routes/api_v1'));

app.all('*', function(req, res, next) {
  res.contentType('application/json');
  res.status(404);
  res.json({"error": "Request endpoint not found"})
});

app.listen(port, function(){
  console.log('The magic happens at https://programmeren-tent-4.herokuapp.com:');
});

module.exports = app;
