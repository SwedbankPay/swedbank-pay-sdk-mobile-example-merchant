'use strict';

module.exports.route = (req, res) => {
  const appIds = global.config.iosAppIds;
  const path = '/sdk-callback/*';

  // Ref: https://stackoverflow.com/a/61232752
  //
  // There are two formats for the AASA file.
  // The newer one is supported on iOS 13 and above,
  // the older one on iOS 12 and below. There is no
  // official documentation on how to combine the formats,
  // but this seems to work.

  const details = appIds.map(id => {
    return {
      appID: id,
      paths: [path]
    }
  });
  if (details.length > 0) {
    details[0].appIDs = appIds;
    details[0].components = [
      {
        '/': path,
        comment: 'Pattern for all iOS Universal Links'
      }
    ];
  }

  const payload = {
    applinks: {
      apps: [],
      details: details
    }
  };
  res.status(200).send(payload).end();
}
