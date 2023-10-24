const socket = new WebSocket('ws://localhost:8080/ws/quiz'); 

const events = {
    READY: "READY",
    START: "START",
    ANSWER: "ANSWER",
    QUESTION: "QUESTION",
    FINISH: "FINISH"
}

socket.onopen = (event) => {
    console.log('Connected to WebSocket');
};

socket.onmessage = (event) => {
    const data = JSON.parse(event.data);
    if (data.event == events.READY) {
        sendMessage(JSON.stringify({username:"erasyl", event: events.START}))
    } else if (data.event == events.QUESTION) {
        displayQuestion(data)
    }
};

socket.onclose = (event) => {
    console.log('WebSocket connection closed:', event.reason);
};

function sendMessage(message) {
    socket.send(message);
}

let selectedAnswer = {username: "", event: events.ANSWER, questionIndex: 0, answer: ""};

document.getElementById('submit-button').addEventListener('click', () => {
    if (selectedAnswer.answer == "") {
        alert("Choose the answer!")
    }
    sendMessage(JSON.stringify(selectedAnswer))
    selectedAnswer.answer = ""
    selectedAnswer.questionIndex = ""
});

function handleAnswerClick(answer) {
    selectedAnswer.answer = answer.option;
    selectedAnswer.questionIndex = answer.currentQuestionIndex
}

function displayQuestion(data) {
    const questionDiv = document.getElementById('question');
    questionDiv.innerText = (data.userSession.currentQuestionIndex + 1) + '. ' + data.questionDto.question;

    const answersDiv = document.getElementById('answers');
    answersDiv.innerHTML = '';

    const answerOptions = [{option: 'A', jsonname: 'answerA'}, 
    {option: 'B', jsonname:'answerB'},
    {option: 'C', jsonname:'answerC'},
    {option: 'D', jsonname:'answerD'}];

    answerOptions.forEach((option) => {
        const answerChild = document.createElement('div');

        const answer = data.questionDto[option.jsonname];
        const radioInput = document.createElement('input');
        radioInput.type = 'radio';
        radioInput.name = 'answer';
        radioInput.value = option.jsonname;
        radioInput.id = option.jsonname;

        const label = document.createElement('label');
        label.htmlFor = option;
        label.innerText = answer;

        radioInput.addEventListener('change', () => {
            handleAnswerClick({option: option.option, currentQuestionIndex: data.userSession.currentQuestionIndex});
        });

        answerChild.appendChild(radioInput);
        answerChild.appendChild(label);
        answersDiv.appendChild(answerChild)
    });

    const rightAnswers = document.getElementById('right-answers');
    rightAnswers.innerText = 'Right answers: ' + data.userSession.rightAnswers;

    const wrongAnswers = document.getElementById('wrong-answers');
    wrongAnswers.innerText = 'Wrong answers: ' + data.userSession.wrongAnswers;
}
