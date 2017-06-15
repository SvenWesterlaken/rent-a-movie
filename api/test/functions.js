var pool = require('../database/db');

function getUsersCount() {
  pool.query('SELECT COUNT(*) FROM customer', function(errors, rows, fields) {
    if (errors) {
      throw errors
    } else {
      return JSON.stringify(rows);
    }
  });
}

function deleteTestUser() {
  pool.query('DELETE FROM customer WHERE customer_id=4', function(errors, rows, fields) {
    if (errors){ throw errors }
  });
}

module.exports = {
  getUsersCount
}
