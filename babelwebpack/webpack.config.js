const HtmlWebpackPlugin = require("html-webpack-plugin");
const CopyPlugin = require("copy-webpack-plugin");
const path = require("path");
console.log(path.resolve(__dirname, "src", "index.js"));
module.exports = {
  // 번들링 모드(development는 개발용)
  mode: "development",
  // 번들링 할 대상
  entry: {
    app: path.resolve(__dirname, "src", "index.js"),
  },
  // 번들링 파일과 경로
  output: {
    filename: "bundle.js",
    path: path.resolve(__dirname, "dist"),
  },
  plugins: [
    new HtmlWebpackPlugin({
     template: "./index.html",
    }),
    new CopyPlugin({
     patterns: [
      { from: "static" }, // static 폴더 안의 내용이 dist로 복사되어 들어가도록 한다.
     ],
    }),
   ], //plugin적용
   devServer: {
    port: 9999,
    hot: true, //수정이 바로바로 적용여부
    host: "localhost", //localhost는 생략가능
    open: true, //true이면 실행시 웹브라우저를 열어줌
   },
};
module: {
  rules: [
    {
      test: /\.html$/, //적용할 파일의 정규식
      use: ["html-loader"], //설치한 loader
    },
    {
      test: /\.css$/,
      use: ["css-loader"],
    },
  ];
}

 
