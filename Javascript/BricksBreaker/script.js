/**
 * Author: Yohan Jin
 * Student Number: 000891436
 * Date: 017 Mar 2023
 * Assignment4 */

// Start the game when the window loads
window.onload = function () {
  // Get the SVG element where the game will be rendered
  const svg = document.getElementById("gameArea");

  // Define constants for game elements dimensions
  const paddleWidth = 100;
  const paddleHeight = 10;
  const ballRadius = 5;
  const brickWidth = 80;
  const brickHeight = 20;
  const pointsPerBrick = 10;

  // Create and append the background rectangle to the SVG
  const background = document.createElementNS("http://www.w3.org/2000/svg", "rect");
  background.setAttribute("width", "100%");
  background.setAttribute("height", "100%");
  background.setAttribute("fill", "white");
  svg.appendChild(background);

  // Create and append the paddle rectangle to the SVG
  const paddle = document.createElementNS("http://www.w3.org/2000/svg", "rect");
  paddle.setAttribute("width", paddleWidth);
  paddle.setAttribute("height", paddleHeight);
  paddle.setAttribute("fill", "blue");
  svg.appendChild(paddle);

  // Create and append the ball circle to the SVG
  const ball = document.createElementNS("http://www.w3.org/2000/svg", "circle");
  ball.setAttribute("r", ballRadius);
  ball.setAttribute("fill", "red");
  svg.appendChild(ball);

  // Create and append the points display text to the SVG
  const pointsDisplay = document.createElementNS("http://www.w3.org/2000/svg", "text");
  pointsDisplay.setAttribute("x", 10);
  pointsDisplay.setAttribute("y", 30);
  pointsDisplay.setAttribute("font-size", "24px");
  pointsDisplay.setAttribute("fill", "black");
  svg.appendChild(pointsDisplay);

  // Initialize bricks and other game variables
  const bricks = [];
  const brickSpacing = 10;
  const totalBrickWidth = 3 * brickWidth + 2 * brickSpacing;
  const startingX = (svg.clientWidth - totalBrickWidth) / 2;
  let points = 0;

  // Define the stages of the game with brick patterns
  const stages = [
    [1, 1, 1],
    [1, 1, 1, 1, 1, 1],
    [1, 1, 1, 1, 1, 1, 1, 1, 1],
  ];
  let currentStage = 0;

  // Function to create bricks for a given stage
  function createBricksForStage(stage) {
    for (let i = 0; i < stage.length; i++) {
      const brick = document.createElementNS("http://www.w3.org/2000/svg", "rect");
      brick.setAttribute("width", brickWidth);
      brick.setAttribute("height", brickHeight);
      brick.setAttribute("x", startingX + (i % 3) * (brickWidth + brickSpacing));
      brick.setAttribute("y", 35 + Math.floor(i / 3) * (brickHeight + brickSpacing));
      brick.setAttribute("fill", "green");
      bricks.push(brick);
      svg.appendChild(brick);
    }
  }

  // Initial positions for paddle and ball
  let paddleX = (svg.clientWidth - paddleWidth) / 2;
  let ballX = paddleX + paddleWidth / 2;
  let ballY = svg.clientHeight - paddleHeight - ballRadius;
  let dx = 2;  // Ball's x-direction speed
  let dy = -2; // Ball's y-direction speed

  // Function to set the paddle's position
  function setPaddlePosition(x) {
    paddleX = Math.max(0, Math.min(svg.clientWidth - paddleWidth, x));
    paddle.setAttribute("x", paddleX);
    paddle.setAttribute("y", svg.clientHeight - paddleHeight);
  }

  // Function to set the ball's position
  function setBallPosition(x, y) {
    ballX = x;
    ballY = y;
    ball.setAttribute("cx", ballX);
    ball.setAttribute("cy", ballY);
  }

  // Function to update the points display
  function updatePointsDisplay() {
    pointsDisplay.textContent = `Points: ${points}`;
  }

  // Function to calculate distance between two points
  function distanceBetweenPoints(x1, y1, x2, y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
  }

  // Function to check collision between ball and brick
  function ballBrickCollision(ball, brick) {
    const brickX = parseFloat(brick.getAttribute("x"));
    const brickY = parseFloat(brick.getAttribute("y"));

    const centerX = brickX + brickWidth / 2;
    const centerY = brickY + brickHeight / 2;

    const distToBrickCenter = distanceBetweenPoints(ballX, ballY, centerX, centerY);

    return distToBrickCenter <= ballRadius + Math.sqrt(Math.pow(brickWidth / 2, 2) + Math.pow(brickHeight / 2, 2)) && ballX >= brickX - ballRadius && ballX <= brickX + brickWidth + ballRadius && ballY >= brickY - ballRadius && ballY <= brickY + brickHeight + ballRadius;
  }

  // Function to check collision between ball and paddle
  function ballPaddleCollision(ball, paddle) {
    const paddleX = parseFloat(paddle.getAttribute("x"));
    const paddleY = parseFloat(paddle.getAttribute("y"));

    return ballX + ballRadius >= paddleX && ballX - ballRadius <= paddleX + paddleWidth && ballY + ballRadius >= paddleY && ballY - ballRadius <= paddleY + paddleHeight;
  }

  // Event listener for mouse movement to control the paddle
  svg.addEventListener("mousemove", function (event) {
    const svgRect = svg.getBoundingClientRect();
    const relativeX = event.clientX - svgRect.left;
    setPaddlePosition(relativeX - paddleWidth / 2);
  });

  // Variables to check if paddle should move left or right
  let movePaddleLeft = false;
  let movePaddleRight = false;

  // Function to update paddle's position based on arrow keys
  function updatePaddlePosition() {
    if (movePaddleLeft) {
      setPaddlePosition(paddleX - 5);
    }
    if (movePaddleRight) {
      setPaddlePosition(paddleX + 5);
    }
  }

  // Event listeners for arrow keys to control paddle movement
  document.addEventListener("keydown", function (event) {
    if (event.key === "ArrowLeft") {
      movePaddleLeft = true;
    } else if (event.key === "ArrowRight") {
      movePaddleRight = true;
    }
  });

  document.addEventListener("keyup", function (event) {
    if (event.key === "ArrowLeft") {
      movePaddleLeft = false;
    } else if (event.key === "ArrowRight") {
      movePaddleRight = false;
    }
  });

  // Function to reset ball's position
  function resetBallPosition() {
    ballX = paddleX + paddleWidth / 2;
    ballY = svg.clientHeight - paddleHeight - ballRadius;
    dx = 2;
    dy = -2;
    setBallPosition(ballX, ballY);
  }

  // Main game update function
  function update() {
    setBallPosition(ballX + dx, ballY + dy);
    updatePaddlePosition();

    // Ball collision with walls
    if (ballX + ballRadius > svg.clientWidth || ballX - ballRadius < 0) {
      dx = -dx;
    }
    if (ballY - ballRadius < 0) {
      dy = -dy;
    }

    // Ball collision with paddle
    if (ballPaddleCollision(ball, paddle)) {
      dy = -dy;
    }

    // Check for ball collision with bricks
    let allBricksGone = true;
    for (let i = 0; i < bricks.length; i++) {
      const brick = bricks[i];
      if (brick) {
        allBricksGone = false;
        if (ballBrickCollision(ball, brick)) {
          dy = -dy;
          svg.removeChild(brick);
          bricks[i] = null;
          points += pointsPerBrick;
          updatePointsDisplay();
        }
      }
    }

    // Check for game over or stage clear conditions
    if (ballY + ballRadius > svg.clientHeight) {
      gameOver();
    }

    if (allBricksGone) {
      if (currentStage < stages.length - 1) {
        alert("CLEAR!");
        const playNext = confirm("Want to try next stage?");
        if (playNext) {
          currentStage++;
          createBricksForStage(stages[currentStage]);
          resetBallPosition();
        } else {
          gameOver();
        }
      } else {
        victory();
      }
    }
  }

  // Game over function
  function gameOver() {
    alert("Game Over");
    clearInterval(interval);
  }

  // Victory function
  function victory() {
    alert("You won!");
    clearInterval(interval);
  }

  // Set initial positions for paddle and ball
  setPaddlePosition(paddleX);
  setBallPosition(ballX, ballY);

  // Start the game loop
  const interval = setInterval(update, 10);

  // Create bricks for the initial stage
  createBricksForStage(stages[currentStage]);
};
