'use strict';

const path = require('path');

module.exports.route = (req, res) => {
  const filePath = path.join(__dirname, '..', "apple-developer-merchantid-domain-association.txt");
  //console.log(filePath)
  
  res.sendFile(filePath, null, function (err) {
    
    if (err) {
      console.log(err)
    }
  })
};