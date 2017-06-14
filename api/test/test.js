var chai = require('chai'),
    chai_http = require('chai-http'),
    server = require('../index.js'),
    should = chai.should();

chai.use(chai_http);

describe('API test', function() {
  it('Test GET /api/v1/film', function(done) {
    chai.request(server)
      .get('/hello')
      .end(function(err, res) {
        res.should.have.status(200);
        res.should.have.property("msg", "Hi, have a nice NodeJS day.");
        done();
      });
  });
});
