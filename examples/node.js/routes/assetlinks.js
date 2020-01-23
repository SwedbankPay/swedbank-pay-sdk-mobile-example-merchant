'use strict';

module.exports.route = (req, res) => {
  const payload = global.config.androidApps.map(app => {
      return {
          relation: ['delegate_permission/common.handle_all_urls'],
          target: {
              namespace: 'android_app',
              package_name: app.package,
              sha256_cert_fingerprints: app.certs
          }
      };
  });
  res.status(200).send(payload).end();
}
