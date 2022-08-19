module.exports = {
  devServer:{
    port:8000
  },
  configureWebpack:{
    externals:{'AMap':"AMap"}
  },
  publicPath:'./'
};