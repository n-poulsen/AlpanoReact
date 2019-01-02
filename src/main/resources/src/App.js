import React, { Component } from 'react';
import './App.css';
import Panorma from './panorama';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
            <h1>
              Alpano
            </h1>
            <div>
              <Panorma
                width={1080}
                height={480}
              />
            </div>
        </header>
      </div>
    );
  }
}

export default App;