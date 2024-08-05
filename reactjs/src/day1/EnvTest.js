import React from 'react';

function EnvTest(props) {
    console.log("경로:" + process.env.PUBLIC_URL);
    console.log(process.env.REACT_APP_CHANNEL_ID);
    console.log(process.env.REACT_APP_CHANNEL_ID2);
    console.log(process.env.REACT_APP_IMAGE_PATH);

    return (
        <div>
            <h1>환경 변수(서버 재시작 필요)</h1>
        </div>
    );
}

export default EnvTest;