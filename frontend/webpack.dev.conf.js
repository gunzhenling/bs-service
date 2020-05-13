const webpack = require('webpack');
const HtmlWebpackPlugin = require("html-webpack-plugin");
// const product = require("../../app/product");
const {target = 3003} = process.env

module.exports = {
  entry: {
    main: './src/main.js'
  },
  optimization: {
    splitChunks: {
      cacheGroups: {
        common: {
          name: 'common',
          minChunks: 2,
          priority: -20,
          chunks: 'initial',
          reuseExistingChunk: true
        }
      }
    }
  },
  plugins: [
    new HtmlWebpackPlugin({
      date: new Date().toLocaleString(),
      title: "管理后台",
      // product: JSON.stringify(product),
      filename: "index.html",
      template: "./public/index.html",
      chunks: ["main"],
      minify: {
        removeComment: true,
        collapseWhitespace: true
      }
    }),
  ],
  devServer: {
		port: 5000,
    proxy: {
      '^/business/': {
        target: 'http://localhost:' + target
      },
      '^/api/': {
        target: 'http://localhost:' + target
      }
    }
  }
}
