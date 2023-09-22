const { createApp } = Vue

const options = {
    data() {
        return {
            loans: [],
            originAccount: null,
            amount: null,
            payments: null,
            accounts: [],
            filterPayments: [],
            selectedLoan: "",
            percentage: ""

        }
    },
    created() {
        this.loadData()
        this.loadDataLoans()
    },
    methods: {
        loadData() {
            axios.get("/api/clients/current/accounts")
                .then(answer => {
                    this.accounts = answer.data
                    console.log(this.accounts);
                }).catch(error => {
                    console.log(error);
                })
        },
        loadDataLoans() {
            axios.get("/api/loans")
                .then(answer => {
                    this.loans = answer.data
                    console.log(this.loans);

                }).catch(error => {
                    console.log(error);
                })
        },
        filterLoansPayments() {
            this.filterPayments = this.loans.filter((loan) => {
                return this.selectedLoan == loan.id
            })[0]
            this.percentage = this.filterPayments.percentage
            console.log(this.percentage);
        },
        sendLoan() {
            const loanDetails = {
                id: this.selectedLoan,
                numberAccountDestination: this.originAccount,
                amount: Number(this.amount),
                payments: this.payments
            }

            Swal.fire({
                title: 'Add a new loan?',
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Yes',
                showLoaderOnConfirm: true,
                buttonColor: '#3085d6',
                preConfirm: login => {
                    return axios.post("/api/loans", loanDetails)
                        .then(answer => {
                            location.reload()
                            alert("The loan was success")
                        }).catch(error => {
                            Swal.fire({
                                icon: 'error',
                                text: error.response.data,
                                confirmButtonColor: '#3085d6'

                            });
                        })
                },
                alloweOutside: () => !Swal.isLoading()
            })
        },
        logOut() {
            axios.post("/api/logout")
                .then(response => {
                    location.href = "../../index.html"
                })
                .catch(error => console.log(error.message))
        },

    }

}

const app = createApp(options)
app.mount('#app')