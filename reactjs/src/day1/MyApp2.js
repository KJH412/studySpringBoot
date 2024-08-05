import React from 'react';
import ExportTest,{constTest,varTest,Func1,Func2} from 'day1/ExportTest'; //default export가 아닌 것들은 {}안에 작성해준다.
import "day1/external.css";

function MyApp2(props) {
    var myname = "리액트JS";
    const score = 99;
    const student = {
        name: "홍길동",
        major:"컴공"
    }
    //▼ 이 부분의 값은 JSX에서 읽지 않음. 출력시 display되지 않는다.
    let score2 = null;
    let score3 = undefined;
    let score4 = false;
    const hobbyArr = ["Sports", "Music", "Movie"]; //배열 중간에 값이 삽입되려면 key가 필요함
    const hobbyDisplay = hobbyArr.map((item,index) => <li key={index}>{item}</li>);

    //import한 값, 함수 사용하기
    console.log(constTest);
    console.log(varTest);

    //css style
    const inlineStyle = {border : "3px dotted blue", color: "orange"};

    return (
        <div style={inlineStyle}>
            <h1 style={{backgroundColor:"green"}}>함수형 Component</h1>
            {/* import연습 */}
            <p className="myStyle2">constTest:{constTest}</p>
            <p className="myStyle3">varTest:{varTest}</p>
            <Func1 />
            <Func2 />
            <ExportTest />
            <p>이름은 {myname}</p>
            <p>점수는 {score}</p>
            <p>student정보 : {student.name} ---- {student.major}</p>
            <p>점수2는 {score2} {score2 == null ? "값 없음(null)" : score2}</p>
            <p>점수3는 {score3} {score3  ?  score3 : "값 없음(undefined)"}</p>
            <p>점수4는 {score4}  {score4 ?  score4 : "값 없음(false)"}</p>
            <div>{hobbyArr}</div>
            <div>
                <ul>
                    {/* 여러 건인 경우 key를 준다. */}
                    {hobbyArr.map((habby, idx) => <li key={idx}>{habby}</li>)}
                </ul>
            </div>
            <p>{hobbyDisplay}</p>    {/* 방법2. 깔끔함 */}
            {/* <p>주석입니다.</p> */}
        </div>
    );
}

export default MyApp2;