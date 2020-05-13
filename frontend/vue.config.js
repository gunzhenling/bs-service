const path = require('path');
const HtmlWebpackPlugin = require("html-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const webpack = require('webpack');
const webpackMerge = require('webpack-merge');

const _env = process.env.NODE_ENV
const isprod = _env === "production"

module.exports = {
  baseUrl:"/", //isprod?"/customer": "/",
  outputDir: isprod ? path.resolve(__dirname, "../dist") : undefined,
  configureWebpack: config => {
    // 为环境修改配置...
    var webpackConfig = isprod
      ? require('./webpack.prod.conf.js')
      : require('./webpack.dev.conf.js')
    config.entry = webpackConfig.entry || config.entry;
    // entry 不能merge会报错提示entry只能是string或array
    delete webpackConfig.entry;
    // 去掉eslint
    config.module.rules.pop()
    if (config.optimization && webpackConfig.optimization) { // dev环境没有optimization
      if (webpackConfig.optimization.splitChunks) {
        config.optimization.splitChunks = webpackConfig.optimization.splitChunks;
      } else {
        config.optimization = webpackConfig.optimization;
      }
    }
    Object.assign(config, webpackMerge(config, webpackConfig));
  },
}
