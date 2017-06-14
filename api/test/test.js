var chai = require('chai'),
    chai_http = require('chai-http'),
    server = require('../index.js'),
    should = chai.should();
    expect = chai.expect;

chai.use(chai_http);

describe('API test', function() {
  it('Test GET /api/v1/film', function(done) {
    chai.request(server)
      .get('/hello')
      .end(function(err, res) {
        expect(res).to.have.status(200);
        expect(res).to.be.json;
        expect(res).to.nested.include({"body.msg": "Hi, have a nice NodeJS day."});
        done();
      });
  });
});
