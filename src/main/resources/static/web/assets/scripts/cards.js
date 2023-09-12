const { createApp } = Vue;

const options = {
    data() {
        return {
            cards: [],
            debits: [],
            credits: [],
            fromDateDebit: [],
            thruDateDebit: [],
            fromDateCredit: [],
            thruDateCredit: []
        }
    }, created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get(`http://localhost:8080/api/clients/current`)
                .then(answer => {
                    this.cards = answer.data.cards

                    this.credits = this.cards.filter(card => card.type == "CREDIT")
                    this.debits = this.cards.filter(card => card.type == "DEBIT")

                    this.fromDateDebit = this.debits.map(date => date.fromDate.slice(2, 7))
                    this.thruDateDebit = this.debits.map(date => date.thruDate.slice(2, 7))
                    

                    this.fromDateCredit = this.credits.map(date => date.fromDate.slice(2, 7))
                    this.thruDateCredit = this.credits.map(date => date.thruDate.slice(2, 7))
                    console.log(this.thruDateCredit);
                }).catch(error => console.log("error"))
        },
        logOut(){
            axios.post("/api/logout")
            .then(response =>{
                location.href = "../../index.html"
            })
            .catch(error=> console.log(error.message))
        }
    }
}

const app = createApp(options)
app.mount("#app")

/*Dark mode */
const switchButton = document.querySelector("#bg-dark");
const body = document.querySelector("body");
switchButton.addEventListener("click", e => {
  body.classList.toggle("dark-mode"); 
});