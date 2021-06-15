/**
 * @type {import('@vue/cli-service').ProjectOptions}
 */
module.exports = {
  outputDir: '../src/main/resources/public',
  devServer: {
    proxy: 'http://localhost:8080/'
  }
}
