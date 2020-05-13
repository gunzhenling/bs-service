const path = require('path');
const HtmlWebpackPlugin = require("html-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
// const product = require("../../app/product");

const date = new Date();
module.exports = {
  entry: {
    // pc: './src/pc.js',
    // mobile: './src/mobile.js',
    main: './src/main.js',
  },
  devtool: false,
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

    // new HtmlWebpackPlugin({
    //   filename: "mobile.html",
    //   template: "./public/index.html",
    //   chunks: [
    //     "common", "vendor", "mobile"
    //   ],
    //   minify: {
    //     removeComment: true,
    //     collapseWhitespace: true
    //   }
    // }),
    // new HtmlWebpackPlugin({
    //   filename: "pc.html",
    //   template: "./public/index.html",
    //   chunks: [
    //     "common", "vendor", "pc"
    //   ],
    //   minify: {
    //     removeComment: true,
    //     collapseWhitespace: true
    //   }
    // }),
  ]
}
