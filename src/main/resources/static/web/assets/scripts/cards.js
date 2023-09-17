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
            thruDateCredit: [],
            cardNumber : "",
            dateName : new Date().toISOString().slice(2, 7),
        }
    }, created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get(`http://localhost:8080/api/clients/current`)
                .then(answer => {
                    console.log(this.dateName);
                    const card = answer.data.cards
                    console.log(card);
                    this.cards = card.filter(card => card.cardStatus == true)

                    this.credits = this.cards.filter(card => card.type == "CREDIT")
                    this.debits = this.cards.filter(card => card.type == "DEBIT")

                    this.fromDateDebit = this.debits.map(date => date.fromDate.slice(2, 7))
                    this.thruDateDebit = this.debits.map(date => date.thruDate.slice(2, 7))
                    

                    this.fromDateCredit = this.credits.map(date => date.fromDate.slice(2, 7))
                    this.thruDateCredit = this.credits.map(date => date.thruDate.slice(2, 7))
                    console.log(this.thruDateCredit);
                }).catch(error => console.log("error"))
        },

        deleteCard(number){
            Swal.fire({
                title: 'Do you want to delete this card?',
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Yes',
                showLoaderOnConfirm: true,
                buttonColor: '#3085d6',
                preConfirm: login => {
                    return axios.patch("http://localhost:8080/api/clients/current/cards", `cardNumber=${number}`)
                        .then(answer => {
                            location.reload()
                        }).catch(error => {
                            Swal.fire({
                                icon: 'error',
                                text:error.response.data,
                                confirmButtonColor: '#3085d6'

                            });
                        })
                },
                alloweOutside:()=> !Swal.isLoading()
            })
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