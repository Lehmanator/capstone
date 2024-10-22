const path = require("path");
const CleanWebpackPlugin = require("clean-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
  entry: path.resolve(__dirname, "app/js/main"),
  devServer: {
    outputPath: path.join(__dirname, "build"),
    historyApiFallback: true
  },
  resolve: {
    extensions: ["*", ".js", ".jsx"]
  },
  output: {
    path: path.resolve(__dirname, "build/js"),
    publicPath: "/js/",
    filename: "bundle.js"
  },
  plugins: [
    //new CleanWebpackPlugin(["build"]),
    new HtmlWebpackPlugin({ template: "./app/static/index.html" }),
    new CopyWebpackPlugin([
      {
        context: path.resolve(__dirname, "app/static"),
        from: "**/*",
        to: path.resolve(__dirname, "build")
      }
    ])
  ],
  module: {
    //preLoaders: [
    //  {
    //    test: /\.jsx?$/,
    //    exclude: /(node_modules|bower_components)/,
    //    loaders: ['eslint'],
    //  },
    //],
    loaders: [
      {
        test: /\.jsx?$/,
        exclude: /(node_modules|bower_components)/,
        loaders: ["babel-loader"]
      },
      {
        test: /\.(gif|png|jpg|svg)$/,
        loader: "file-loader",
        options: {
          name: "[path][name].[ext]"
        }
      }
    ]
  }
};
