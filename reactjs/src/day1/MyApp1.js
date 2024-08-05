import React from 'react';
import Header from 'day1/Header';
import Content from 'day1/Menu';  //Menu.js 안에 Content.js
import Section from 'day1/Section';

//Component : 재사용 가능, 반드시 대문자, 외부에서 사용하려면 export
function MyApp1(props) {
    //JSX(JavaScript XML) : xml은 반드시 root가 한개, tag는 반드시 닫는다.
    return (
        <div>
            <h1>Function Component</h1>
            <Header />
            <Content />
            <Content />
            <Section />
            <hr />
        </div>
    );
}

export default MyApp1;