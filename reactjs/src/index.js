import React from 'react';
import ReactDOM from 'react-dom/client';
import EnvTest from 'day1/EnvTest';
import MyApp1 from 'day1/MyApp1';
import MyApp2 from 'day1/MyApp2';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <div>
        <h1>ReactJs 시작하기</h1>
        <EnvTest />
        <MyApp1 />
        <MyApp2 />
    </div>
);
