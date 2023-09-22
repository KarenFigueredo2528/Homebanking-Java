const { createApp } = Vue;

const options = {
  data() {
    return {
      accounts: [],
      description: "",
      amount: 0,
      originAccount: "",
      destinationAccount: "",
      form1: true,
      form2: false,
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    showOwnAccounts() {
      this.form1 = true;
      this.form2 = false;
    },
    showExternalAccounts() {
      this.form1 = false;
      this.form2 = true;
    },
    loadData() {
      axios.get("/api/clients/current/accounts")
        .then((answer) => {
          const account = answer.data
          this.accounts = account.filter(account => account.accountStatus == true);
          console.log(this.accounts);
        });
    },
    transfer() {
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
          return axios.post("/api/transactions", `amount=${this.amount}&description=${this.description}&originAccount=${this.originAccount}&finalAccount=${this.destinationAccount}`)
            .then(answer => {
              alert("Transfer Succesful")
              location.reload()
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
  },
};

const app = createApp(options);
app.mount("#app");
