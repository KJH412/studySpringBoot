import logo from './logo.svg';
import './App.css';
import loadsh from  "loadsh";


function App() {
  var scores = [1,1,1,1,1,2,2,2,2,4,4,4,4,4,1,1,1,2,2,2];
  var result = loadsh.uniqBy(scores);
  console.log(result);
  
  return (
    <div className="App">
      <header className="App-header">
        <h1>App 컴포넌트이다.</h1>
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
