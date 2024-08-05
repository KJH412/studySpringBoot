const arr = Array.from("HELLO");
arr.map((ch)=>console.log(ch));
const myname="jin";
const age=25
const person = {newname:myname, age};
const {newname} = person;
const age2 = person.age;

console.log(newname);
console.log(person.age);

export default function f_hello() {
     return "헬로우 웹팩";
}
    