const { createApp } = Vue;

const options = {
  data() {
    return {
      color: "",
      type: "",
    };
  },
  created() {},
  methods: {
    alert(event) {
      event.preventDefault();
      let opcion = confirm("Do you want to create a new card?");
      if (opcion == true) {
        console.log(this.color);
        console.log(this.type);
        axios.post('/api/clients/current/cards',`color=${this.color}&type=${this.type}`,{headers:{'content-type': 'application/x-www-form-urlencoded'}})
          .then(response => {
            console.log(response);
            location.href = "../pages/cards.html";
          }).catch((error) => {
            window.alert("You have reached the card limit");    
          });
      }
    },
  },
};

const app = createApp(options);
app.mount("#app");
