const { createApp } = Vue;

const options = {
  data() {
    return {
      accounts: [],
      description:"",
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
      axios.get("http://localhost:8080/api/accounts")
        .then((answer) => {
        this.accounts = answer.data;
        console.log(this.accounts);
      });
    },
    transfer(){
        axios.post("http://localhost:8080/api/transactions",`amount=${this.amount}&description=${this.description}&originAccount=${this.originAccount}&finalAccount=${this.destinationAccount}`)
        .then(answer => {
            alert("Transfer Succesful")
            window.location.reload()
        }).catch((error => 
            alert(error.response.data)
            ));   
    }
  },
};

const app = createApp(options);
app.mount("#app");
