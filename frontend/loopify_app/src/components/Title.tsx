import React from 'react';
import './Title.css';

function Title() {

  return (
      <div className='title'>
          <svg viewBox="0 0 1440 320">
              <path id="curve"
                    d="M0,96L48,122.7C96,149,192,203,288,218.7C384,235,480,213,576,197.3C672,181,768,171,864,192C960,213,1056,267,1152,277.3C1248,288,1344,256,1392,240L1440,224L1440,0L1392,0C1344,0,1248,0,1152,0C1056,0,960,0,864,0C768,0,672,0,576,0C480,0,384,0,288,0C192,0,96,0,48,0L0,0Z" />
              <text width="500" text-anchor="right">
                  <textPath xlinkHref="#curve" startOffset="17%">
                      LOOPIFY
                  </textPath>
              </text>
          </svg>
      </div>
  );

}

export default Title;