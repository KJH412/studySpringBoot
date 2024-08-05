import React from 'react';

export const constTest = 100;
export var varTest = "문자값";
export function Func1(){ return <p>함수1</p>; } //대문자기 때문에 컴포넌트로 사용 가능하다.
export const Func2 = () => { return <p>함수2</p>; };


export default function ExportTest(props) {
    return (
        <div>
            <div>
                <p>ExportTest함수(default export)</p>
            </div>
        </div>
    );
}

// export default ExportTest;
// 각각 export예약어를 사용할 수 도 있고 한번에 작성해도 된다.
// export {ExportTest ad default, constTest, varTest, Func1, Func2};