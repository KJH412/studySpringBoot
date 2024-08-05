"use strict";

var arr = Array.from("HELLO");
arr.map(function (ch) {
  return console.log(ch);
});
var myname = "jin";
var age = 25;
var person = {
  newname: myname,
  age: age
};
var newname = person.newname;
var age2 = person.age;
console.log(newname);
console.log(person.age);